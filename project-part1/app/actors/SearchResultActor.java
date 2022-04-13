package actors;

import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithTimers;
import akka.actor.ActorRef;
// import model.Searchphraseresult;
import models.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
// import

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SearchResultActor extends AbstractActorWithTimers
{
    @Inject
    private ActorRef userActor;

  //  private FreelancelotService freelancelotService;

    private String query;

    private final Logger logger = LoggerFactory.getLogger("play");

    private Set<SearchResult> searchphraseresultItems;

    public static final class Tick {
    }
        @Override
        public void preStart()
        {
            getTimers().startPeriodicTimer("Time" ,new Tick(),
                    Duration.create(10 , TimeUnit.SECONDS));
        }

        public SearchResultActor(){
        this.userActor = null;
        this.query = null;
        }

        @Override
        public Receive createReceive(){
            return receiveBuilder()
                    .match(Messages.RegisterActor.class , message ->{
                        logger.info("Registering actor()" , message);
                        userActor  = sender();
                        getSender().tell("userActor registered" , getSelf());
            })
            .match(Messages.WatchSearchResults.class , message -> {
                logger.info("Received message WatchSearchResults {}" , message);
                if(message != null && message.query != null && message.query != ""){
                    // watchSerachResult(message);
                }
            })
                    .match(Tick.class, message ->{
                        logger.info("Received message Tick{}" , message);
                        if(query != null){
                            // tickSearchResults();
                        }
                    })
                    .build();
        }

        // public CompletionStage<void> watchSearchResult(Messages.WatchSearchResults message){
        // query = message.query;
        // return

        // }


        public String getQuery(){
            return  query;
        }
        public void setQuery(String query){
            this.query = query;
        }

}
