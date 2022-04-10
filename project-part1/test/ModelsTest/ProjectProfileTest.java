package ModelsTest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
*The class ProjectProfileTest
*@author Esha
*/
public class ProjectProfileTest {
	
	/**
	 * testSetTitle: Test of setTitle method, of class ProjectProfile.
	 */
	@Test
	public void testSetTitle() {
	    String title = "Node js";
	    ProjectProfile instance = new ProjectProfile();
	    instance.setTitle(title);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getTitle(), title);
	}

	/**
	 * testGetTitle: Test of getTitle method, of class ProjectProfile.
	 */
	@Test
	public void testGetTitle() {
		ProjectProfile instance = new ProjectProfile();
	    String title = "1234";
	    instance.setTitle("1234");
	    String result = instance.getTitle();
	    assertEquals(title, result);
	}

	/**
	 * testSetDescription: Test of setDescription method, of class ProjectProfile.
	 */
	@Test
	public void testSetDescription() {
	    String previewDescription = "Node js";
	    ProjectProfile instance = new ProjectProfile();
	    instance.setDescription(previewDescription);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getDescription(), previewDescription);
	}

	/**
	 * testGetDescription: Test of getDescription method, of class ProjectProfile.
	 */
	@Test
	public void testGetDescription() {
		ProjectProfile instance = new ProjectProfile();
	    String previewDescription = "Node js";
	    instance.setDescription("Node js");
	    String result = instance.getDescription();
	    assertEquals(previewDescription, result);
	}

	/**
	 * testSetLast_modify_date: Test of setLastmodifydate method, of class ProjectProfile.
	 */
	@Test
	public void testSetLast_modify_date() {
		String date = "12-21-2021";
		ProjectProfile instance = new ProjectProfile();
	    instance.setLastmodifydate(date);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getLastmodifydate(), date);
	}

	/**
	 * testGetLast_modify_date: Test of getLastmodifydate method, of class ProjectProfile.
	 */
	@Test
	public void testGetLast_modify_date() {
		String date = "12-21-2021";
		ProjectProfile instance = new ProjectProfile();
	    instance.setLastmodifydate("12-21-2021");
	    String result = instance.getLastmodifydate();
	    assertEquals(date, result);
	}
	
	/**
	 * testSetPortfolio_id: Test of setPortfolioid method, of class ProjectProfile.
	 */
	@Test
	public void testSetPortfolio_id() {
	    String portfolio_id = "1234";
	    ProjectProfile instance = new ProjectProfile();
	    instance.setPortfolioid(portfolio_id);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getPortfolioid(), portfolio_id);
	}

	/**
	 * testGetPortfolio_id: Test of getPortfolioid method, of class ProjectProfile.
	 */
	@Test
	public void testGetPortfolio_id() {
		ProjectProfile instance = new ProjectProfile();
	    String expResult = "1234";
	    instance.setPortfolioid("1234");
	    String result = instance.getPortfolioid();
	    assertEquals(expResult, result);
	}
}
