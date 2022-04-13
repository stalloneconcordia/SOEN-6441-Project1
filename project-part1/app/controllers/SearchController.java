package controllers;

import com.google.inject.Inject;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import models.FreelancerClient;
import models.SearchHistory;
import models.SearchResult;
import models.SearchProfile;
import models.ProfileInformation;
import models.GlobalStats;
import play.api.cache.AsyncCacheApi;
import play.api.data.Form;
import play.i18n.MessagesApi;
import play.data.FormFactory;
import play.mvc.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import play.libs.ws.WSClient;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;
// import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import static akka.pattern.Patterns.ask;
import scala.compat.java8.FutureConverters;
import akka.actor.Props;


/**
 * @author Swapnil, Stallone, Esha, Saumya
 * @version 1: Swapnil, Stallone, Esha, Saumya  implements the project framework, search, and other features.
 */
public class SearchController extends Controller {

        public static final String SESSION_ID = "session_id";

        private final FreelancerClient freelancer;
        private final play.data.Form<SearchForm> searchForm;
        private final MessagesApi messagesApi;
        private final SearchHistory searchHistory;
        private final WSClient client;
        private AsyncCacheApi cache;
        private ActorRef searchPhraseActor;
//  private ActorRef childSearchPhraseActor;
        private ActorRef profileActor = null;
        private ActorRef skillActor = null;
        private ActorRef statsActor = null;
        private String sessionID;
        // ActorSystem system = ActorSystem.create("FreeLancelot");

    /**
     *
     * The SearchController Constructor
     * @author Swapnil, Stallone, Esha, Saumya
     */
        @Inject
        public SearchController(FreelancerClient freelancer, FormFactory formFactory, MessagesApi messagesApi, AsyncCacheApi asyncCacheApi,ActorSystem system, WSClient client) {
            System.out.println("inside searchcontroller constructor.....");
            // this.searchPhraseActor = system.actorOf(FreelancerClient.props(client), "search");
            this.freelancer = freelancer;
            this.searchForm = formFactory.form(SearchForm.class);
            this.messagesApi = messagesApi;
            this.searchHistory = new SearchHistory();
            this.client = client;
            this.cache = asyncCacheApi;
            this.profileActor = system.actorOf(FreelancerClient.props(client));
            this.skillActor = system.actorOf(FreelancerClient.props(client));
            this.statsActor = system.actorOf(FreelancerClient.props(client));

            // this.searchPhraseActor = system.actorOf(FreelancerClient.getProps());

        }

    /**
     * The homepage which displays the search history of the current session
     * @author Swapnil, Stallone, Esha, Saumya
     * @param request takes http request as an arguement
     */
    @Inject
    ActorSystem system = ActorSystem.create("FreeLancelot");
    public CompletionStage<Result> index(Http.Request request) {
        System.out.println("in index.x....");

        sessionID = request.session().get(SESSION_ID).orElseGet(() -> UUID.randomUUID().toString());
        System.out.println("in index.x....22....");
        List<SearchResult> searchResults = searchHistory.getHistory(sessionID);

        System.out.println("in index.x....333....");
        return CompletableFuture.completedFuture(
                ok(views.html.index.render(searchResults, searchForm, request, messagesApi.preferred(request)))
                        .addingToSession(request, SESSION_ID, sessionID));
    }
    /**
     * An endpoint that performs a search and adds the result to the history for the current session
     * @author Swapnil, Stallone, Esha, Saumya
     * @param request takes http request as an arguement
     * @return SearchController.index
     */
    public CompletionStage<Result> search(Http.Request request) {
        play.data.Form<SearchForm> boundForm = searchForm.bindFromRequest(request);
        if (boundForm.hasErrors()) {
            System.out.println("Here..");
            return CompletableFuture.completedFuture(redirect(routes.SearchController.index()));
        } else {
            // searchPhraseActor = system.actorOf()
            System.out.println("Here..111");
            // searchPhraseActor = system.actorOf(SearchPhrase.props(sessionId));
            
            String searchInput = boundForm.get().getInput();
            sessionID = request.session().get(SESSION_ID).orElseGet(() -> UUID.randomUUID().toString());
            searchPhraseActor = system.actorOf(FreelancerClient.props(client));
           
            System.out.println("[INFO] new Actor Created"+searchPhraseActor);
            // String arr = new String[10];
            
            
            return FutureConverters.toJava(ask(searchPhraseActor, searchInput, Integer. MAX_VALUE))
                    .thenApplyAsync(response -> {
                        // LinkedHashMap<String, Resultlist> resultmap = null;
                        try{
                            System.out.println(response);
                            // resultmap = (LinkedHashMap<String, Resultlist>) response;
                        }catch(Exception e){
                            return ok("Internal Server Error");
                        }
                        return ok(views.html.index.render((List<SearchResult>)response,searchForm, request, messagesApi.preferred(request)));
                    });
            
                    // .thenApply(response -> {
                    //     System.out.println("argagf....");
                    // try{
                    //     // CompletionStage<SearchResult> resultmap = null;
                    //     System.out.println(response);
                    //     // System.out.println(response.getClassName().getSimpleName());
                    //     // resultmap = response;
                    // }
                    // catch (Exception e){
                    //     e.printStackTrace();
                    // });
                    // arr[
                        // return ok(views.html.index.render(resultmap));
                    // .addingToSession(request, SESSION_ID, sessionId);
                // return freelancer.searchProjects(searchInput)
                //         .thenAccept(searchResult -> searchHistory.addToHistory(sessionId, searchResult))
                //         .thenApplyAsync(nullResult -> redirect(routes.SearchController.index())
                //                 .addingToSession(request, SESSION_ID, sessionId));
            
            // return CompletableFuture.completedFuture(redirect(routes.SearchController.index()));
        }
    }

    /**
     * Controller Method for api: /profile/:ownerid
     * displays the information of the owners and hyperlinks to the details of an owner
     * @author Esha
     * @param ownerId takes owner id as an arguement
     * @return  profileInformation displays profile information for an owner id
     */
    public CompletionStage<Result> profileInfo(String  ownerId) throws JsonGenerationException, JsonMappingException{
        // CompletionStage<List<SearchProfile>> res = freelancer.getOwnerProfile(ownerId);
        // return res.thenApplyAsync(o -> ok(views.html.profileInformation.render(o)));
        return FutureConverters.toJava(ask(profileActor, ownerId, Integer. MAX_VALUE))
                .thenApply(response -> {
                    // LinkedHashMap<String, Profile> resultmap = null;
                    try{
                        resultmap = (List<SearchProfile>) response;
                    }catch(Exception e){
                    }
                    return ok(views.html.profile.render(resultmap));
                });
    }

    /**
     * Controller Method for api: /globalStats
     * displays the preview description stats like word count
     * @author Swapnil
     * @param keyword takes a key word as an arguement
     * @return  globalStats returns stats for the keyword
     */
    public CompletionStage<Result> globalStats(String keyword){  
//      Map<String,Integer> stats;
//        stats = freelancer.getGlobalStats(keyword);
//        return CompletableFuture.completedFuture(
//                ok(views.html.globalStats.render(freelancer.getGlobalStats(keyword))));
        // CompletionStage<GlobalStats> res = freelancer.getGlobalStats(keyword);
        // return res.thenApplyAsync(o -> ok(views.html.globalStats.render(o)));
        return FutureConverters.toJava(ask(statsActor, keyword, Integer.MAX_VALUE))
                .thenApply(response -> {
                    // LinkedHashMap<String, Resultlist> resultmap = null;
                    try{
                        // resultmap = (LinkedHashMap<String, Resultlist>) response;
                    }catch(Exception e){
                        return ok("Internal Server Error");
                    }
                    return ok(views.html.stat.render(response));
                });
    
    }


    /**
     * Controller Method for api: /projectsIncludingSkill
     * displays the projects for a given skill
     * @author Stallone
     * @param id takes id as an arguement
     * @param skill takes skill as an arguement
     * @return  projectsWithSkills
     */
    public CompletionStage<Result> projectsIncludingSkill(int id, String skill){
        Integer y = new Integer(id);
        // CompletionStage<SearchResult> answer = freelancer.projectsIncludingSkill(Integer.toString(y), skill);
        // return answer.thenApplyAsync(o -> ok(views.html.projectsWithSkills.render("Search term",o)));
        return FutureConverters.toJava(ask(skillActor, id,skill, Integer. MAX_VALUE))
                .thenApply(response -> {
                    // LinkedHashMap<String, Resultlist> resultmap = null;
                    try{skill,
                        System.out.println(response);
                        // resultmap = (LinkedHashMap<String, Resultlist>) response;
                    }catch(Exception e){
                        return ok("Internal Server Error");
                    }
                    return ok(views.html.skill.render(response));
                });
    }

    /**
     * Controller Method for api: /projectStat
     * displays the statistics from preview description of a particular project
     * @author Stallone,Saumya,Swapnil,Esha
     * @param prev_desc Preview desrciption as an arguement
     * @return  projectStats
     */
    public Result projectStats(String prev_desc){
        Map<String,Integer> temp;
        temp = freelancer.getProjectStats(prev_desc);
        return ok(views.html.projectStats.render(temp));
    }

/**
 * Controller Method for api: /readability/:prev_desc
 * displays the readability index  from preview description
 * @author Saumya
 * @param prev_desc Preview desrciption as an arguement
 * @return  result

 */
   public Result readability(String prev_desc) throws JsonGenerationException, JsonMappingException{
        HashMap<String,Float> fdata = freelancer.getReadabilityIndex(prev_desc);
        return ok(views.html.readability.render(fdata));
    }
}
