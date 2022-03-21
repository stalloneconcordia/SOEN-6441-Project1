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
import java.util.List;
import java.util.UUID;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class SearchController extends Controller {

        public static final String SESSION_ID = "session_id";

        private final FreelancerClient freelancer;
        private final play.data.Form<SearchForm> searchForm;
        private final MessagesApi messagesApi;
        private final SearchHistory searchHistory;

        private AsyncCacheApi cache;

        @Inject
        public SearchController(FreelancerClient freelancer, FormFactory formFactory, MessagesApi messagesApi, AsyncCacheApi asyncCacheApi) {
            this.freelancer = freelancer;
            this.searchForm = formFactory.form(SearchForm.class);
            this.messagesApi = messagesApi;
            this.searchHistory = new SearchHistory();
            this.cache = asyncCacheApi;

        }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public CompletionStage<Result> index(Http.Request request) {

        String sessionId = request.session().get(SESSION_ID).orElseGet(() -> UUID.randomUUID().toString());
        List<SearchResult> searchResults = searchHistory.getHistory(sessionId);
        return CompletableFuture.completedFuture(
                ok(views.html.index.render(searchResults, searchForm, request, messagesApi.preferred(request)))
                        .addingToSession(request, SESSION_ID, sessionId));
    }

    public CompletionStage<Result> search(Http.Request request) {
        play.data.Form<SearchForm> boundForm = searchForm.bindFromRequest(request);
        if (boundForm.hasErrors()) {
            return CompletableFuture.completedFuture(redirect(routes.SearchController.index()));
        } else {
            String searchInput = boundForm.get().getInput();
            String sessionId = request.session().get(SESSION_ID).orElseGet(() -> UUID.randomUUID().toString());
            try{
                return freelancer.searchRepositories(searchInput)
                        .thenAccept(searchResult -> searchHistory.addToHistory(sessionId, searchResult))
                        .thenApplyAsync(nullResult -> redirect(routes.SearchController.index())
                                .addingToSession(request, SESSION_ID, sessionId));
            }catch (Exception e){
                e.printStackTrace();
            }
            return CompletableFuture.completedFuture(redirect(routes.SearchController.index()));
        }
    }

    public CompletionStage<Result> profileInfo(String  ownerId) throws JsonGenerationException, JsonMappingException{
        CompletionStage<List<SearchProfile>> res = freelancer.getOwnerProfile(ownerId);
        return res.thenApplyAsync(o -> ok(views.html.profileInformation.render(o)));
    }

    public CompletionStage<Result> globalStats(String keyword){  
//      Map<String,Integer> stats;
//        stats = freelancer.getGlobalStats(keyword);
//        return CompletableFuture.completedFuture(
//                ok(views.html.globalStats.render(freelancer.getGlobalStats(keyword))));
        CompletionStage<GlobalStats> res = freelancer.getGlobalStats(keyword);
        return res.thenApplyAsync(o -> ok(views.html.globalStats.render(o)));
    
    }

    public CompletionStage<Result> projectsIncludingSkill(int id, String skill){
        Integer y = new Integer(id);
        CompletionStage<SearchResult> answer = freelancer.projectsIncludingSkill(Integer.toString(y), skill);
        return answer.thenApplyAsync(o -> ok(views.html.projectsWithSkills.render("Search term",o)));
    }

    public Result projectStats(String prev_desc){
        Map<String,Integer> temp;
        temp = freelancer.getProjectStats(prev_desc);
        return ok(views.html.projectStats.render(temp));
    }


    public HashMap<String,Float> getReadabilityIndex (String prev_desc){
        HashMap<String,Float> fdata = new HashMap<String,Float>();
        List<String> prev_desc_list = new ArrayList<>();
        prev_desc_list.add(prev_desc);
        long total_words = prev_desc_list.stream()
                .map(w -> w.replaceAll("\\p{Punct}", "").split(" "))
                .flatMap(Arrays::stream)
                .count();

        long total_sentences = prev_desc_list.stream()
                .map(w -> w.split("[!?.:]+"))
                .flatMap(Arrays::stream)
                .count();
        int total_syllables = countSyllables(prev_desc_list.get(0));
        if (total_sentences > 0 && total_words > 0 && total_syllables > 0) {
            double FRI = (206.835 - 84.6)*((double)total_syllables/(double)total_words) - 1.015 * ((double)total_words/(double)total_sentences);
            float FRI_value = (float)(FRI);
            fdata.put("Flesch Readability Index",FRI_value);
            double FREL = 0.39*((double)total_words/(double)total_sentences) + 11.8*((double)total_syllables/(double)total_words) - 15.59;

            float FREL_value = (float)(FREL);
            fdata.put("Flesch Readability Education Level",FREL_value);
            return fdata;
        }
        else{
            float FRI_Value = 0;
            float FREL_Value = 0;
            fdata.put("Flesch Readability Index",FRI_Value);
            fdata.put("Flesch Readability Education Level",FREL_Value);
            return fdata;
        }
    }

    public static int countSyllables(final String word) {
        return Math.max(1, word.toLowerCase()
                .replaceAll("e$", "")
                .replaceAll("[aeiouy]{2}", "a")
                .replaceAll("[aeiouy]{2}", "a")
                .replaceAll("[^aeiouy]", "")
                .length());
    }

}
