package ModelsTest;

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

import models.FreelancerClient;
import models.GlobalStats;
import models.ProfileInformation;
import models.ProjectProfile;
import models.Projects;
import models.SearchHistory;
import models.SearchProfile;
import models.SearchResult;
/** 
*The class SearchProfileTest
*@author Group
*/

public class SearchProfileTest {

	/**
	 * testSetInput: Test of setInput method, of class SearchProfile.
	 */
	@Test
	public void testSetInput() {
	    String input = "python";
	    SearchProfile instance = new SearchProfile();
	    instance.setInput(input);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getInput(), input);
	}

	/**
	 * testGetInput: Test of getInput method, of class SearchProfile.
	 */
	@Test
	public void testGetInput() {
		SearchProfile instance = new SearchProfile();
	    String input = "python";
	    instance.setInput("python");
	    String result = instance.getInput();
	    assertEquals(input, result);
	}

	/**
	 * testSetProfile_information: Test of setProfileInformation method, of class SearchProfile.
	 */
	@Test
	public void testSetProfile_information() {
		List<ProfileInformation> profileInfo = new ArrayList<>();
		ProfileInformation obj = new ProfileInformation();
		obj.setPublicName("name");
		obj.setOwner_id("1234");
		obj.setUsername("user");
		obj.setRegistration_date("09-09-2022");
		obj.setLimited_account("limit");
		obj.setDisplay_name("display");
		obj.setCountry("country");
		obj.setRole("role");
		obj.setEmail_status("verify");
		obj.setAccepted_currency("rupees");
		List<ProjectProfile> projectProfile = new ArrayList<>();
		ProjectProfile instance = new ProjectProfile();
		instance.setTitle("title");
		instance.setDescription("description");
		instance.setLastmodifydate("07-08-2022");
		instance.setPortfolioid("1234");
		projectProfile.add(instance);

		obj.setProjectProfile(projectProfile);

		profileInfo.add(obj);
		SearchProfile search = new SearchProfile();
		search.setProfileInformation(profileInfo);
		assertEquals(search.getProfileInformation(), profileInfo);
	}

	/**
	 * testGetProfile_information: Test of getProfileInformation method, of class SearchProfile.
	 */
	@Test
	public void testGetProfile_information() {
		List<ProfileInformation> profileInfo = new ArrayList<>();
		ProfileInformation obj = new ProfileInformation();
		obj.setPublicName("name");
		obj.setOwner_id("1234");
		obj.setUsername("user");
		obj.setRegistration_date("09-09-2022");
		obj.setLimited_account("limit");
		obj.setDisplay_name("display");
		obj.setCountry("country");
		obj.setRole("role");
		obj.setEmail_status("verify");
		obj.setAccepted_currency("rupees");
		List<ProjectProfile> projectProfile = new ArrayList<>();
		ProjectProfile instance = new ProjectProfile();
		instance.setTitle("title");
		instance.setDescription("description");
		instance.setLastmodifydate("07-08-2022");
		instance.setPortfolioid("1234");
		projectProfile.add(instance);
		obj.setProjectProfile(projectProfile);
		profileInfo.add(obj);
	
		List<ProfileInformation> profileInfo1 = new ArrayList<>();
		ProfileInformation obj1 = new ProfileInformation();
		obj1.setPublicName("name");
		obj1.setOwner_id("1234");
		obj1.setUsername("user");
		obj1.setRegistration_date("09-09-2022");
		obj1.setLimited_account("limit");
		obj1.setDisplay_name("display");
		obj1.setCountry("country");
		obj1.setRole("role");
		obj1.setEmail_status("verify");
		obj1.setAccepted_currency("rupees");
		List<ProjectProfile> projectProfile1 = new ArrayList<>();
		ProjectProfile instance1 = new ProjectProfile();
		instance1.setTitle("title");
		instance1.setDescription("description");
		instance1.setLastmodifydate("07-08-2022");
		instance1.setPortfolioid("1234");
		projectProfile1.add(instance1);

		obj1.setProjectProfile(projectProfile1);

		profileInfo1.add(obj1);
		SearchProfile search = new SearchProfile();
		search.setProfileInformation(profileInfo1);

		assertEquals(search.getProfileInformation(), profileInfo1);
	}





















}