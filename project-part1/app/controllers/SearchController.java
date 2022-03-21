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
 * @author Swapnil, Stallone, Esha, Saumya
 * @version 1: Swapnil, Stallone, Esha, Saumya  implements the project framework, search, and other features.
 */
public class SearchController extends Controller {

        public static final String SESSION_ID = "session_id";

        private final FreelancerClient freelancer;
        private final play.data.Form<SearchForm> searchForm;
        private final MessagesApi messagesApi;
        private final SearchHistory searchHistory;

        private AsyncCacheApi cache;

    /**
     *
     * The SearchController Constructor
     * @author Swapnil, Stallone, Esha, Saumya
     */
        @Inject
        public SearchController(FreelancerClient freelancer, FormFactory formFactory, MessagesApi messagesApi, AsyncCacheApi asyncCacheApi) {
            this.freelancer = freelancer;
            this.searchForm = formFactory.form(SearchForm.class);
            this.messagesApi = messagesApi;
            this.searchHistory = new SearchHistory();
            this.cache = asyncCacheApi;

        }

    /**
     * The homepage which displays the search history of the current session
     * @author Swapnil, Stallone, Esha, Saumya
     * @param request takes http request as an arguement
     */
    public CompletionStage<Result> index(Http.Request request) {

        String sessionId = request.session().get(SESSION_ID).orElseGet(() -> UUID.randomUUID().toString());
        List<SearchResult> searchResults = searchHistory.getHistory(sessionId);
        return CompletableFuture.completedFuture(
                ok(views.html.index.render(searchResults, searchForm, request, messagesApi.preferred(request)))
                        .addingToSession(request, SESSION_ID, sessionId));
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
            return CompletableFuture.completedFuture(redirect(routes.SearchController.index()));
        } else {
            String searchInput = boundForm.get().getInput();
            String sessionId = request.session().get(SESSION_ID).orElseGet(() -> UUID.randomUUID().toString());
            try{
                return freelancer.searchProjects(searchInput)
                        .thenAccept(searchResult -> searchHistory.addToHistory(sessionId, searchResult))
                        .thenApplyAsync(nullResult -> redirect(routes.SearchController.index())
                                .addingToSession(request, SESSION_ID, sessionId));
            }catch (Exception e){
                e.printStackTrace();
            }
            return CompletableFuture.completedFuture(redirect(routes.SearchController.index()));
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
        CompletionStage<List<SearchProfile>> res = freelancer.getOwnerProfile(ownerId);
        return res.thenApplyAsync(o -> ok(views.html.profileInformation.render(o)));
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
        CompletionStage<GlobalStats> res = freelancer.getGlobalStats(keyword);
        return res.thenApplyAsync(o -> ok(views.html.globalStats.render(o)));
    
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
        CompletionStage<SearchResult> answer = freelancer.projectsIncludingSkill(Integer.toString(y), skill);
        return answer.thenApplyAsync(o -> ok(views.html.projectsWithSkills.render("Search term",o)));
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
