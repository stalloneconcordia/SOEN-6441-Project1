package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import akka.actor.AbstractActor;
import akka.actor.Props;

// import akka.http.javadsl.model.headers.Date;
import models.Projects;
import java.util.List;


public class SearchResult{

    private String input;
    private List<Projects> projects;
    //    private String
    private float index;
    private String level;

    // public static Props getProps(){
    //     return Props.create(SearchResult.class);
    // }

    // @Override
    // public Receive createReceive() {

    //     return receiveBuilder()
    //             .match(String.class, a -> {
    //                 LinkedHashMap<String, Profile> pup = getProfileResult(a);
    //                 sender().tell(pup, self());
    //             })
    //             .build();
    // }

    /**
     * SearchResult constructor
     */
    public SearchResult() {
        System.out.println("inside non para contsr.");
    }
    public SearchResult(String input, List<Projects> projects)  {
        this.input = input;
        this.projects = projects;
    }
    /**
     * The getter of Index
     * @return index
     */
    public float getIndex() {
        return index;
    }

    /**
     * The setter of Index
     * @param index takes index as an arguement
     */
    public void setIndex(float index) {
        this.index = index;
    }

    /**
     * The getter of level
     * @return  level
     */
    public String getLevel() {
        return level;
    }
    /**
     * The setter of level
     * @param level takes index as an arguement
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * The getter of Input
     * @return  input
     */
    public String getInput() {
        return input;
    }
    /**
     * The setter of Input
     * @param input takes index as an arguement
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * The getter of projects
     * @return  projects
     */
    public List<Projects> getProjects() {
        return projects;
    }



    //    @JsonSetter("projects")
    /**
     * The setter of projects
     * @param projects takes index as an arguement
     */
    public void setProjects(List<Projects> projects) {
        System.out.println("In set repositories");
        this.projects = projects;
    }
}