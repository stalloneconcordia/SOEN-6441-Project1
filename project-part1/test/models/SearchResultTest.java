package models;

import static org.junit.Assert.assertEquals;
import models.Projects;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

/** 
*The class SearchResultTest
*@author Stallone
*/
public class SearchResultTest {

	/**
	 * Test of setInput method, of class SearchResult.
	 */
	@Test
	public void testSetInput() {
	    String input = "python";
	    SearchResult instance = new SearchResult();
	    instance.setInput(input);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getInput(), input);
	}

	/**
	 * Test of getInput method, of class SearchResult.
	 */
	@Test
	public void testGetInput() {
		SearchResult instance = new SearchResult();
	    String input = "python";
	    instance.setInput("python");
	    String result = instance.getInput();
	    assertEquals(input, result);
	}
	
	/**
	 * Test of setProjects method, of class SearchResult.
	 */
	@Test
	public void testSetProjects() {
		HashMap<String, Integer> skills = new HashMap<String, Integer>();
		Projects project = new Projects();
		skills.put("Hello", 1);
		project.setSkills(skills);
        project.setOwner("owner");
        project.setTitle("title");
        project.setType("type");
        project.setPrevDesc("preview_description");
        project.setDate("12-12-2020");
        project.setSeo_url("seo_url");
        project.setType("type");
        List<Projects> projects = new ArrayList<Projects>();
        projects.add(project);
	    SearchResult instance = new SearchResult();
	    instance.setProjects(projects);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getProjects(), projects);
	}

	/**
	 * Test of getProjects method, of class SearchResult.
	 */
	@Test
	public void testGetProjects() {
		SearchResult instance = new SearchResult();
		List<Projects> projects = new ArrayList<>();
		HashMap<String, Integer> skills = new HashMap<String, Integer>();
		Projects project = new Projects();
		skills.put("Hello", 1);
		project.setSkills(skills);
        project.setOwner("owner");
        project.setTitle("title");
        project.setType("type");
        project.setPrevDesc("preview_description");
        project.setDate("12-12-2020");
        project.setSeo_url("seo_url");
        project.setType("type");
        projects.add(project);
        List<Projects> projects1 = null;
		HashMap<String, Integer> skills1 = new HashMap<String, Integer>();
		Projects project1 = new Projects();
		skills.put("Hello", 1);
		project.setSkills(skills1);
        project.setOwner("owner");
        project.setTitle("title");
        project.setType("type");
        project.setPrevDesc("preview_description");
        project.setDate("12-12-2020");
        project.setSeo_url("seo_url");
        project.setType("type");
        projects.add(project1);
	    instance.setProjects(projects1);
	    List<Projects> projects2 = instance.getProjects();
	    assertEquals(projects1, projects2);
	}
	
	
}
