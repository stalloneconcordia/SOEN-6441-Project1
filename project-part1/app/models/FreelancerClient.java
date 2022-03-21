package models;

import java.util.*;
import java.util.concurrent.CompletionStage;

import com.google.gson.*;
import play.libs.Json;
import com.google.inject.Inject;

import play.libs.ws.WSBodyReadables.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.typesafe.config.Config;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

import play.cache.AsyncCacheApi;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Class FreelancerClient
 * @author Saumya,Stallone,Esha,Swapnil
 * The FreelancerClient class, to hold the content for a Freelancer client
 */
public class FreelancerClient {
    /** The WSClient client */
    private final WSClient client;

    /** The String baseURL*/
    private final String baseURL;

    private final AsyncCacheApi cache;

    /** The Constructor*/
    @Inject
    public FreelancerClient(WSClient client, AsyncCacheApi cache, Config config) {
        this.client = client;
        this.cache = cache;
        this.baseURL = config.getString("freelancer.url");
    }

    /**
     * The method searchRepositories, to search the repositories based on the given query and whether it's a topic
     * @author Swapnil,Stallone,Esha,Saumya
     * @param query the given query
     * @return the search results
     */
    public CompletionStage<SearchResult> searchProjects(String query) throws JsonGenerationException, JsonMappingException {
//      https://www.freelancer.com/api/projects/0.1/projects/active/?query=
        String freelancerQuery = query;
        return cache.getOrElseUpdate("search://" + freelancerQuery, () -> {
            WSRequest req = client.url(baseURL + "/projects/0.1/projects/active");
//          System.out.println(Json.fromJson((req.addQueryParameter("query", freelancerQuery).get()).asJson(), SearchResult.class));
            return req
                    .addHeader("freelancelotESAPP", "UzhSBUrlZiSK4o8yQ8CA8ZyJ36VRvh")
                    .addQueryParameter("query", freelancerQuery)
                    .addQueryParameter("compact", "false")
                    .addQueryParameter("job_details", "true")
                    .addQueryParameter("limit", "10")
                    .get()
                    .thenApplyAsync(WSResponse::asJson)
                    .thenApplyAsync(r -> {
                        ArrayList<Projects> projectsList = new ArrayList<>();
//                        System.out.println("r is here" + r);
                        int f = 0;
                        while (r.get("result").get("projects").get(f) != null) {
//                            System.out.println("new job " +  r.get("result").get("projects").get(f).get("jobs").asText().getClass().getSimpleName());
                            Projects project = new Projects();
                            JsonNode skills = r.get("result").get("projects").get(f).get("jobs");
                            ;
                            HashMap<String, Integer> skillsData = new HashMap<>();
                            for (int i = 0; i < skills.size(); i++) {
                                int id = skills.get(i).get("id").asInt();
                                String skillName = skills.get(i).get("name").asText();
                                if (skillsData.containsKey(id)) {
                                    continue;
                                } else {
                                    skillsData.put(skillName, id);
                                }

                            }
                            project.setSkills(skillsData);
                            project.setOwner(r.get("result").get("projects").get(f).get("owner_id").asText());
                            project.setTitle(r.get("result").get("projects").get(f).get("title").asText());
                            project.setType(r.get("result").get("projects").get(f).get("type").asText());
                            project.setPrevDesc(r.get("result").get("projects").get(f).get("preview_description").asText());

                            Date date = new Date(r.get("result").get("projects").get(f).get("submitdate").asLong() * 1_000L);
                            DateFormat simple = new SimpleDateFormat("MMM dd yyyy");
                            project.setDate(simple.format(date));
                            project.setSeo_url(r.get("result").get("projects").get(f).get("seo_url").asText());
                            project.setType(r.get("result").get("projects").get(f).get("type").asText());
                            projectsList.add(project);
                            f++;
                        }
                        SearchResult searchResult = new SearchResult();
                        searchResult.setInput(query);
                        searchResult.setProjects(projectsList);
                        return searchResult;
                    });
        }, 4000);
    }

    /**
     * The method getOwnerProfile, to get the Owner Profile details for a given owner id
     * @author Esha
     * @param owner_id takes owner id as an arguement
     * @return the liSearch
     */
    public CompletionStage<List<SearchProfile>> getOwnerProfile (String owner_id) throws JsonGenerationException, JsonMappingException {
        SearchProfile searchProfile = new SearchProfile();
        ProfileInformation profileInformation = new ProfileInformation();
        List<ProfileInformation> profile = new ArrayList<ProfileInformation>();
        List<SearchProfile> liSearch = new ArrayList<SearchProfile>();
        return  cache.getOrElseUpdate("search://" + owner_id,() -> {
            WSRequest req = client.url(baseURL+"/users/0.1/portfolios/?limit=10&compact=true&portfolio_details=true&user_details=true&user_qualification_details=true&user_jobs=true&user_portfolio_details=true&user_recommendations=true&count=true&user_profile_description=true&users[]="+owner_id);
            return req
                    .addHeader("freelancelotESAPP","UzhSBUrlZiSK4o8yQ8CA8ZyJ36VRvh")
                    .addQueryParameter("limit","10")
                    .get()
                    .thenApplyAsync(WSResponse::asJson)
                    .thenApplyAsync(r-> {
                        profileInformation.setUsername(r.get("result").get("users").get(owner_id).get("username").asText());
                        profileInformation.setPublicName(r.get("result").get("users").get(owner_id).get("public_name").asText());
                        profileInformation.setOwner_id(r.get("result").get("users").get(owner_id).get("id").asText());
                        profileInformation.setRegistration_date(r.get("result").get("users").get(owner_id).get("registration_date").asText());
                        profileInformation.setLimited_account(r.get("result").get("users").get(owner_id).get("limited_account").asText());
                        profileInformation.setDisplay_name(r.get("result").get("users").get(owner_id).get("display_name").asText());
                        profileInformation.setCountry(r.get("result").get("users").get(owner_id).get("location").get("country").get("name").asText());
                        profileInformation.setRole(r.get("result").get("users").get(owner_id).get("role").asText());
                        profileInformation.setEmail_status(r.get("result").get("users").get(owner_id).get("status").get("email_verified").asText());
                        profileInformation.setAccepted_currency(r.get("result").get("users").get(owner_id).get("primary_currency").get("name").asText());
                        JsonNode  portfolios = r.get("result").get("portfolios").get(owner_id);
                        if(portfolios == null){
                            List<ProjectProfile> pinfoList = new ArrayList<ProjectProfile>();
                            ProjectProfile pp = new ProjectProfile();
                            pp.setTitle("No Title");
                            pp.setDescription("No Description");
                            Date date = new Date(0000000000 *1_000L);
                            DateFormat simple = new SimpleDateFormat("dd MMM yyyy");
                            pp.setLastmodifydate(simple.format(date));
                            pp.setPortfolioid("No ID");
                            pinfoList.add(pp);
                            profileInformation.setProjectProfile(pinfoList);
                            profile.add(profileInformation);
                            searchProfile.setProfileInformation(profile);
                            liSearch.add(searchProfile);
                            return liSearch;

                        }
                        else{
                            List<ProjectProfile> pinfoList = new ArrayList<ProjectProfile>();
                            for (int i = 0; i < portfolios.size(); i++) {
                                ProjectProfile pp = null;
                                pp = new ProjectProfile();
                                pp.setTitle(portfolios.get(i).get("title").asText());
                                pp.setDescription(portfolios.get(i).get("description").asText());
                                Date date = new Date(portfolios.get(i).get("last_modify_date").asLong() * 1_000L);
                                DateFormat simple = new SimpleDateFormat("dd MMM yyyy");
                                pp.setLastmodifydate(simple.format(date));
                                pp.setPortfolioid(portfolios.get(i).get("id").toString());
                                pinfoList.add(pp);
                            }
                            profileInformation.setProjectProfile(pinfoList);
                            profile.add(profileInformation);
                            searchProfile.setProfileInformation(profile);
                            liSearch.add(searchProfile);
                            return liSearch;
                        }
//                        System.out.println(portfolios.asText());

                    } );
        },4000);


    }

    /**
     *displays the projects for a given skill
     * @author Stallone
     * @param id Takes id as an arguement
     * @param skill Takes skill as an arguement
     * @return searchResult
     */
    public CompletionStage<SearchResult> projectsIncludingSkill(String id, String skill){
        return cache.getOrElseUpdate("search://"+skill,()->{
            WSRequest req = client.url(baseURL+"/projects/0.1/projects/active");
//          System.out.println(Json.fromJson((req.addQueryParameter("query", freelancerQuery).get()).asJson(), SearchResult.class));
            return req
                    .addHeader("freelancelotESAPP","UzhSBUrlZiSK4o8yQ8CA8ZyJ36VRvh")
                    .addQueryParameter("jobs[]",id)
                    .addQueryParameter("compact","false")
                    .addQueryParameter("job_details","true")
                    .addQueryParameter("limit","10")
                    .get()
                    .thenApplyAsync(WSResponse::asJson)
                    .thenApplyAsync(r-> {
                        ArrayList<Projects> projectsList = new ArrayList<>();
                        int f = 0;
                        while (r.get("result").get("projects").get(f) != null) {
                            Projects project = new Projects();
                            JsonNode  skills = r.get("result").get("projects").get(f).get("jobs");;
                            HashMap<String,Integer> skillsData = new HashMap<>();
                            for(int i = 0 ; i<skills.size() ; i++){
                                int id1 = skills.get(i).get("id").asInt();
                                String skillName = skills.get(i).get("name").asText();
                                if(skillsData.containsKey(id)){
                                    continue;
                                }
                                else{
                                    skillsData.put(skillName,id1);
                                }

                            }
                            project.setSkills(skillsData);
                            project.setOwner(r.get("result").get("projects").get(f).get("owner_id").asText());
                            project.setTitle(r.get("result").get("projects").get(f).get("title").asText());
                            project.setType(r.get("result").get("projects").get(f).get("type").asText());
                            project.setPrevDesc(r.get("result").get("projects").get(f).get("preview_description").asText());

                            Date date = new Date(r.get("result").get("projects").get(f).get("submitdate").asLong() * 1_000L);
                            DateFormat simple = new SimpleDateFormat("MMM dd yyyy");
                            project.setDate(simple.format(date));
                            project.setSeo_url(r.get("result").get("projects").get(f).get("seo_url").asText());
                            project.setType(r.get("result").get("projects").get(f).get("type").asText());
                            projectsList.add(project);
                            f++;
                        }
                        SearchResult searchResult = new SearchResult();
                        searchResult.setInput(skill);
                        searchResult.setProjects(projectsList);
                        return searchResult;
                       } );
                    },4000);    
                    }

    /**
     * displays the statistics from preview description of a particular project
     * @author Stallone,Saumya,Swapnil,Esha
     * @param prev_desc takes preview description as an arguement
     * @return  wordCounter
     */
    public Map<String, Integer> getProjectStats(String prev_desc){
        List <String> list = Stream.of(prev_desc).map(w -> w.split("\\s+")).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        Map <String, Integer > wordCounter = list.stream()
                .collect(Collectors.toMap(w -> w.toLowerCase(), w -> 1, Integer::sum));
        return wordCounter;
    }

    /**
     * displays the preview description stats like word count
     * @author Swapnil
     * @param searchkeyword takes a keyword as an arguement
     * @return  globalStats
     */
    public CompletionStage<GlobalStats> getGlobalStats(String searchkeyword){
            WSRequest req = client.url(baseURL+"/projects/0.1/projects/active");
            return req
                    .addHeader("freelancelotESAPP","UzhSBUrlZiSK4o8yQ8CA8ZyJ36VRvh")
                    .addQueryParameter("query",searchkeyword)
                    .addQueryParameter("preview_description","true")
                    .addQueryParameter("limit","250")
                    .get()
                    .thenApplyAsync(WSResponse::asJson)
                    .thenApplyAsync(r-> {
                        List<String> descriptionList = new ArrayList<>();
                        int f = 0;
                      while (r.get("result").get("projects").get(f) != null) {
                          descriptionList.add(r.get("result").get("projects").get(f).get("preview_description").asText());
                          f++;
                      }
                      String allwords ="";
                      for (int i = 0; i < descriptionList.size(); i++) {
                          allwords = allwords+ descriptionList.get(i);
                      }
                      List <String> words = Stream.of(allwords).
                                map(a -> ((String) a)
                                .replaceAll("[^a-zA-Z ]", "")
                                .split("\\s+")).flatMap(Arrays::stream)
                                .collect(Collectors.toList());
                      
                      Map <String, Integer> wordCounter = words.stream()
                              .collect(Collectors.toMap(w -> w.toLowerCase(), w -> 1, Integer::sum));
                        
                      GlobalStats globalstats = new GlobalStats();
                      Map<String, Integer> temp = new LinkedHashMap<>();
                      wordCounter.entrySet().stream()
                              .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                              .forEachOrdered(x -> temp.put(x.getKey(), x.getValue()));
                      globalstats.setSortedWordCounter(temp);
                      return globalstats;
            
                    });
        }

}







