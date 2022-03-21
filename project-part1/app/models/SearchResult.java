package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

// import akka.http.javadsl.model.headers.Date;
import models.Projects;
import java.util.List;


public class SearchResult {

    private String input;
    private List<Projects> projects;
    //    private String
    private float index;
    private float level;
    public SearchResult() {
        System.out.println("inside non para contsr.");
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float index) {
        this.index = index;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }


    public List<Projects> getProjects() {
        return projects;
    }


    //    @JsonSetter("projects")
    public void setProjects(List<Projects> projects) {
        System.out.println("In set repositories");
        this.projects = projects;
    }
}