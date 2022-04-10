package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;


public class ProjectProfile {

    String title;
    String description;
    String lastmodifydate;
    String portfolioid;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLastmodifydate() {
        return lastmodifydate;
    }
    public void setLastmodifydate(String lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }
    // @Override
    // public String toString() {
    //     return "projectInfo [title=" + title + ", description=" + description + ", lastmodifydate=" + lastmodifydate
    //             + ", portfolioid=" + portfolioid + "]";
    // }
    public String getPortfolioid() {
        return portfolioid;
    }
    public void setPortfolioid(String portfolioid) {
        this.portfolioid = portfolioid;
    }

    

}
