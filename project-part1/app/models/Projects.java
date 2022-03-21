package models;

/**
 * Hold a project consisting of title, owner, preview description, date, type, seo_url and skills.
 * @author Stallone Mecwan
 * @version 1: Stallone Mecwan implements the project framework, search, and topic feature.
 * The Project class to hold the content of a project
 */
import java.util.*;

public class Projects{

    public String owner;

    public String title;

    public String prev_description;

    public String date;

    public String type;
    public String seo_url;

    public HashMap<String,Integer> skills;

    /**
     * The empty constructor for json
     * @author Stallone Mecwan
     */
    public Projects(){

    }

    public Projects(String owner, String title, String prev_description,String type, String date, HashMap<String,Integer> skills){
        this.owner = owner;
        this.title = title;
        this.prev_description = prev_description;
        this.type = type;
        this.date = date;
        this.skills = skills;
    }
    /**
     * The getter of Owner
     * @author Stallone Mecwan
     * @return owner
     
     */
    public String getOwner() {
        return owner;
    }

    /**
     * The getter of Seo_url
     * @author Stallone Mecwan
     * @return seo_url
     */
    public String getSeo_url(){
        return seo_url;
    }
    /**
     * The setter of Seo_url
     * @author Stallone Mecwan
     * @param seo_url setter for seo url
     */
    public void setSeo_url(String seo_url){this.seo_url = seo_url; }

    /**
     * The setter of Skills
     * @author Stallone Mecwan
     * @param skills setter for set skills
     */
    public void setSkills(HashMap<String,Integer> skills){

        this.skills = skills;
    }

    /**
     * The setter of  Owner
     * @author Stallone Mecwan
     * @param owner setter for owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * The getter of Date
     * @author Stallone Mecwan
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * The setter of Date
     * @author Stallone Mecwan
     * @param date setter for date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * The getter of Title
     * @author Stallone Mecwan
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * The setter of Title
     * @author Stallone Mecwan
     * @param title setter for title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * The getter of Type
     * @author Stallone Mecwan
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * The setter of Type
     * @author Stallone Mecwan
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The getter of Skills
     * @author Stallone Mecwan
     * @return skills
     */
    public HashMap<String,Integer> getSkills() {
        return skills;
    }

    /**
     * The setter of Preview Description
     * @author Stallone Mecwan
     * @param prevDesc setter for preview description
     */
    public void setPrevDesc(String prevDesc) {
        this.prev_description = prevDesc;
    }

    /**
     * The getter of Preview Description
     * @author Stallone Mecwan
     * @return prev_description
     */
    public String getPrevDesc() {
        return prev_description;
    }


}