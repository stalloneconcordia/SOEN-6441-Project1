package actors;

import actors.Messages.UnwatchSearchResults;
import actors.Messages.WatchSearchResults;
import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Pair;
import akka.stream.KillSwitches;
import akka.stream.Materializer;
import akka.stream.UniqueKillSwitch;
import akka.stream.javadsl.*;
// import model.Searchphraseresult;
import models.SearchResult;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.Json;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionStage;

/**
 * The broker between the WebSocket and the SearchResultsActor(s).
 * The UserActor holds the connection and sends serialized JSON data to the client.

 */
public class UserActor extends AbstractActor {

    private final Logger logger = LoggerFactory.getLogger("play");

    private Map<String, UniqueKillSwitch> searchResultsMap = new HashMap<>();

    private Map<String, ActorRef> searchResultsActors;

    private Materializer mat;

    private Sink<JsonNode, NotUsed> hubSink;

    private Sink<JsonNode, CompletionStage<Done>> jsonSink;

    private Flow<JsonNode, JsonNode, NotUsed> websocketFlow;

    private Injector injector;

    /**
     * Default empty constructor for the tests
     */
    public UserActor() {
        searchResultsActors = null;
        mat = null;
        hubSink = null;
        websocketFlow = null;
        injector = null;
    }


    /**
     * Regular constructor
     * @param injector Guice Injector, used later to create the SearchResultsActor with GuiceInjectedActor
     * @param mat Materializer for the Akka streams
     */
    @Inject
    public UserActor(Injector injector, Materializer mat) {
        this.searchResultsActors = new HashMap<>();
        this.mat = mat;
        this.injector = injector;
        createSink();
    }

    /**
     * Create the Akka Sink
     */
    public void createSink() {
        Pair<Sink<JsonNode, NotUsed>, Source<JsonNode, NotUsed>> sinkSourcePair =
                MergeHub.of(JsonNode.class, 16)
                        .toMat(BroadcastHub.of(JsonNode.class, 256), Keep.both())
                        .run(mat);

        hubSink = sinkSourcePair.first();
        Source<JsonNode, NotUsed> hubSource = sinkSourcePair.second();

        jsonSink = Sink.foreach((JsonNode json) -> {

            String queryRequest = json.findPath("query").asText();
            askForItems(queryRequest);
        });


        this.websocketFlow = Flow.fromSinkAndSourceCoupled(jsonSink, hubSource)
                .watchTermination((n, stage) -> {

                    searchResultsActors.forEach((query, actor) -> stage.thenAccept(f -> context().stop(actor)));

                    stage.thenAccept(f -> context().stop(self()));

                    return NotUsed.getInstance();
                });
    }



    /**
     * If there already exists a SearchResultsActor for the keyword we want, ask it for updates
     * Otherwise, create a new one, register the UserActor and wait the results
     * @param query
     */
    private void askForItems(String query) {
        ActorRef actorForQuery = searchResultsActors.get(query);
        if (actorForQuery != null) {
            actorForQuery.tell(new WatchSearchResults(query), self());
        } else {
//            actorForQuery = getContext().actorOf(Props.create(GuiceInjectedActor.class, injector,
//                    SearchResultsActor.class));
            searchResultsActors.put(query, actorForQuery);
            actorForQuery.tell(new Messages.RegisterActor(), self());
            actorForQuery.tell(new WatchSearchResults(query), self());
        }
    }

    /**
     * The receive block, useful for the manipulation of the flow by the actor
     */
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(WatchSearchResults.class, watchSearchResults -> {
                    logger.info("Received message WatchSearchResults {}", watchSearchResults);
                    if (watchSearchResults != null) {
                        askForItems(watchSearchResults.query);
                        sender().tell(websocketFlow, self());
                    }
                })
                .match(UnwatchSearchResults.class, unwatchSearchResults -> {
                    logger.info("Received message UnwatchSearchResults {}", unwatchSearchResults);
                    if (unwatchSearchResults != null) {
                        searchResultsMap.get(unwatchSearchResults.query).shutdown();
                        searchResultsMap.remove(unwatchSearchResults.query);
                    }
                })
                .match(Messages.SearchResult_actor.class, message -> {
                    logger.info("Received message RepoItem {}", message);
                    if (message != null) {
                        addRepoItems(message);
                        sender().tell(websocketFlow, self());
                    }
                })
                .build();
    }


    public void addRepoItems(Messages.SearchResult_actor searchResult) {
        Set<SearchResult> searchResults = searchResult.searchResults;
        String query = searchResult.query;

        logger.info("Adding statuses {} for query {}", searchResults, query);

        Source<JsonNode, NotUsed> getSource = Source.from(searchResults)
                .map(Json::toJson);

        final Flow<JsonNode, JsonNode, UniqueKillSwitch> killswitchFlow = Flow.of(JsonNode.class)
                .joinMat(KillSwitches.singleBidi(), Keep.right());
        String name = "searchresult-" + query;
        final RunnableGraph<UniqueKillSwitch> graph = getSource
                .viaMat(killswitchFlow, Keep.right())
                .to(hubSink)
                .named(name);

        UniqueKillSwitch killSwitch = graph.run(mat);

        searchResultsMap.put(query, killSwitch);
    }


    /**
     * Factory interface to create a UserActor from the UserParentActor
     */
    public interface Factory {
        Actor create(String id);
    }

    /**
     * Setter for Materializer
     * @param mat Materializer
     */
    public void setMat(Materializer mat) {
        this.mat = mat;
    }

    /**
     * Getter for the SearchResultsMap
     * @return a Map containing the kill switches for a query
     */
    public Map<String, UniqueKillSwitch> getSearchResultsMap() {
        return searchResultsMap;
    }

    /**
     * Setter for the SearchResultsMap
     * @param searchResultsMap SearchResultsMap
     */
    public void setSearchResultsMap(Map<String, UniqueKillSwitch> searchResultsMap) {
        this.searchResultsMap = searchResultsMap;
    }

    /**
     * Getter for the Materializer
     * @return Materializer
     */
    public Materializer getMat() {
        return mat;
    }
    /**
     * Getter for the json sink
     * @return jsonSink a Sink of JsonNodes and CompletionStage of Done
     */

    public Sink<JsonNode, CompletionStage<Done>> getJsonSink() {
        return jsonSink;
    }

    /**
     * Setter for the json sink
     * @param jsonSink Sink of JsonNode and CompletionStage of Done
     */
    public void setJsonSink(Sink<JsonNode, CompletionStage<Done>> jsonSink) {
        this.jsonSink = jsonSink;
    }

    /**
     * Getter for the SearchResultsActor map
     * @return searchResultsActors Map of String and ActorRef a map of actor references for a given query
     */
    public Map<String, ActorRef> getSearchResultsActors() {
        return searchResultsActors;
    }

    /**
     * Setter for the SearchResultsActor map
     * @param searchResultsActors Maps the String and ActorRef for a given query
     */
    public void setSearchResultsActors(Map<String, ActorRef> searchResultsActors) {
        this.searchResultsActors = searchResultsActors;
    }
}