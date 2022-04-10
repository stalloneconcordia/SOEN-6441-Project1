package ModelsTest;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;
import models.FreelancerClient;
import models.GlobalStats;
import models.ProfileInformation;
import models.ProjectProfile;
import models.Projects;
import models.SearchHistory;
import models.SearchProfile;
import models.SearchResult;
/** 
*The class ProjectsTest
*@author Stallone
*/
public class ProjectsTest {

	/**
	 * testSetOwner: Test of setOwner method, of class Projects.
	 */
	@Test
	public void testSetOwner() {
	    String owner = "John";
	    Projects instance = new Projects();
	    instance.setOwner(owner);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getOwner(), owner);
	}

	/**
	 * testGetOwner: Test of getOwner method, of class Projects.
	 */
	@Test
	public void testGetOwner() {
	    Projects instance = new Projects();
	    String expResult = "Mike";
	    instance.setOwner("Mike");
	    String result = instance.getOwner();
	    assertEquals(expResult, result);
	}

	/**
	 * testSetTitle: Test of setTitle method, of class Projects.
	 */
	@Test
	public void testSetTitle() {
	    String title = "John";
	    Projects instance = new Projects();
	    instance.setTitle(title);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getTitle(), title);
	}

	/**
	 * testGetTitle: Test of getTitle method, of class Projects.
	 */
	@Test
	public void testGetTitle() {
//	    System.out.println("setFlightNumber");
	    String title = "Mike";
	    Projects instance = new Projects();
	    instance.setTitle("Mike");
	    String result = instance.getTitle();
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(result, title);
	}
	
	/**
	 * testSetType: Test of setType method, of class Projects.
	 */
	@Test
	public void testSetType() {
	    String type = "fixed";
	    Projects instance = new Projects();
	    instance.setType(type);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getType(), type);
	}
	
	/**
	 * testGetType: Test of getType method, of class Projects.
	 */
	@Test
	public void testGetType() {
	    String type = "fixed";
	    Projects instance = new Projects();
	    instance.setType("fixed");
	    String result = instance.getType();
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(result, type);
	}
	
	/**
	 * testSetPreviewDescription: Test of setPrevDesc method, of class Projects.
	 */
	@Test
	public void testSetPreviewDescription() {
	    String desc = "description";
	    Projects instance = new Projects();
	    instance.setPrevDesc(desc);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getPrevDesc(), desc);
	}

	/**
	 * testGetPreviewDescription: Test of getPrevDesc method, of class Projects.
	 */
	@Test
	public void testGetPreviewDescription() {
	    String desc = "Mike";
	    Projects instance = new Projects();
	    instance.setPrevDesc("Mike");
	    String result = instance.getPrevDesc();
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(result, desc);
	}
	
	/**
	 * testSetDate: Test of setDate method, of class Projects.
	 */
	@Test
	public void testSetDate() {
	    String date = "12/03/2022";
	    Projects instance = new Projects();
	    instance.setDate(date);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getDate(), date);
	}
	
	/**
	 * testGetDate: Test of getDate method, of class Projects.
	 */
	@Test
	public void testGetDate() {
	    String date = "12/03/2022";
	    Projects instance = new Projects();
	    instance.setDate("12/03/2022");
	    String result = instance.getDate();
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(result, date);
	}
	
	/**
	 * testSetSeoUrl: Test of setSeo_url method, of class Projects.
	 */
	@Test
	public void testSetSeoUrl() {
	    String seoUrl = "freelancer.com";
	    Projects instance = new Projects();
	    instance.setSeo_url(seoUrl);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getSeo_url(), seoUrl);
	}
	
	/**
	 * testGetSeoUrl: Test of getSeo_url method, of class Projects.
	 */
	@Test
	public void testGetSeoUrl() {
	    String seoUrl = "freelancer.com";
	    Projects instance = new Projects();
	    instance.setSeo_url("freelancer.com");
	    String result = instance.getSeo_url();
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(result, seoUrl);
	}
	
	/**
	 * testSetSkills: Test of setSkills method, of class Projects.
	 */
	@Test
	public void testSetSkills() {
		HashMap<String, Integer> skills = new HashMap<>();
		skills.put("hi", 1);
		Projects instance = new Projects();
	    instance.setSkills(skills);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getSkills(), skills);
	}
	
	/**
	 * testGetSkills: Test of getSkills method, of class Projects.
	 */
	@Test
	public void testGetSkills() {
		HashMap<String, Integer> skills = new HashMap<>();
		skills.put("hi", 1);
		Projects instance = new Projects();
		HashMap<String, Integer> skills1 = new HashMap<>();
		skills1.put("hi", 1);
	    instance.setSkills(skills1);
	    HashMap<String, Integer> result = instance.getSkills();
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(result, skills);
	}
	
	

}

