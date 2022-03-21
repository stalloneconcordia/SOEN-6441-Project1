package models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/** 
*The class ProfileInformationTest
*@author Esha
*/
public class ProfileInformationTest {
	
	/**
	 * testSetOwner_id: Test of setOwner_id method, of class ProfileInformation.
	 */
	@Test
	public void testSetOwner_id() {
	    String owner = "1234";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setOwner_id(owner);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getOwner_id(), owner);
	}

	/**
	 * testGetOwner_id: Test of getOwner_id method, of class ProfileInformation.
	 */
	@Test
	public void testGetOwner_id() {
	    ProfileInformation instance = new ProfileInformation();
	    String expResult = "1234";
	    instance.setOwner_id("1234");
	    String result = instance.getOwner_id();
	    assertEquals(expResult, result);
	}
	
	/**
	 * testSetUsername: Test of setUsername method, of class ProfileInformation.
	 */
	@Test
	public void testSetUsername() {
	    String username = "Stallone";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setUsername(username);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getUsername(), username);
	}
	/**
	 * testGetUsername: Test of getUsername method, of class ProfileInformation.
	 */
	
	@Test
	public void testGetUsername() {
	    ProfileInformation instance = new ProfileInformation();
	    String username = "Stallone";
	    instance.setUsername("Stallone");
	    String result = instance.getUsername();
	    assertEquals(username, result);
	}

	/**
	 * testSetRegistration_date: Test of setRegistration_date method, of class ProfileInformation.
	 */
	@Test
	public void testSetRegistration_date() {
	    String date = "12/03/2022";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setRegistration_date(date);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getRegistration_date(),date);
	}

	/**
	 * testGetRegistration_date: Test of getRegistration_date method, of class ProfileInformation.
	 */	
	@Test
	public void testGetRegistration_date() {
	    ProfileInformation instance = new ProfileInformation();
	    String date = "12/03/2022";
	    instance.setRegistration_date("12/03/2022");
	    String result = instance.getRegistration_date();
	    assertEquals(date, result);
	}
	
	/**
	 * testSetLimited_account: Test of setLimited_Account method, of class ProfileInformation.
	 */
	@Test
	public void testSetLimited_account() {
	    String limited_account = "yes";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setLimited_account(limited_account);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getLimited_account(),limited_account);
	}
	
	/**
	 * testGetLimited_account: Test of getLimited_Account method, of class ProfileInformation.
	 */
	@Test
	public void testGetLimited_account() {
	    ProfileInformation instance = new ProfileInformation();
	    String limited_account = "yes";
	    instance.setLimited_account("yes");
	    String result = instance.getLimited_account();
	    assertEquals(limited_account, result);
	}
	
	/**
	 * testSetDisplayName: Test of setDisplay_name method, of class ProfileInformation.
	 */
	@Test
	public void testSetDisplayName() {
	    String display_name = "Stallone";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setDisplay_name(display_name);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getDisplay_name(),display_name);
	}
	
	/**
	 * testGetDisplay_name: Test of getDisplay_name method, of class ProfileInformation.
	 */
	@Test
	public void testGetDisplay_name() {
	    ProfileInformation instance = new ProfileInformation();
	    String display_name = "Stallone";
	    instance.setDisplay_name("Stallone");
	    String result = instance.getDisplay_name();
	    assertEquals(display_name, result);
	}
	
	/**
	 * testSetCountry: Test of setCountry method, of class ProfileInformation.
	 */
	@Test
	public void testSetCountry() {
	    String country = "Canada";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setCountry(country);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getCountry(),country);
	}
	
	/**
	 * testGetCountry: Test of getCountry method, of class ProfileInformation.
	 */
	@Test
	public void testGetCountry() {
	    ProfileInformation instance = new ProfileInformation();
	    String country = "Canada";
	    instance.setCountry("Canada");
	    String result = instance.getCountry();
	    assertEquals(country, result);
	}
	
	/**
	 * testSetRole: Test of setRole method, of class ProfileInformation.
	 */
	@Test
	public void testSetRole() {
	    String role = "developer";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setRole(role);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getRole(),role);
	}
	
	/**
	 * testGetRole: Test of getRole method, of class ProfileInformation.
	 */
	@Test
	public void testGetRole() {
	    ProfileInformation instance = new ProfileInformation();
	    String role = "developer";
	    instance.setRole("developer");
	    String result = instance.getRole();
	    assertEquals(role, result);
	}
	
	/**
	 * testSetEmail_status: Test of setEmail_status method, of class ProfileInformation.
	 */
	@Test
	public void testSetEmail_status() {
	    String email_status = "active";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setEmail_status(email_status );
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getEmail_status(),email_status);
	}
	
	/**
	 * testGetEmail_status: Test of getEmail_status method, of class ProfileInformation.
	 */
	@Test
	public void testGetEmail_status() {
	    ProfileInformation instance = new ProfileInformation();
	    String email_status = "active";
	    instance.setEmail_status("active");
	    String result = instance.getEmail_status();
	    assertEquals(email_status, result);
	}
	
	/**
	 * testSetAcceptedCurrency: Test of setAccepted_currency method, of class ProfileInformation.
	 */
	@Test
	public void testSetAcceptedCurrency() {
	    String accepted_currency = "dollar";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setAccepted_currency(accepted_currency);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getAccepted_currency(),accepted_currency);
	}
	
	/**
	 * testGetAcceptedCurrency: Test of getAccepted_currency method, of class ProfileInformation.
	 */
	@Test
	public void testGetAcceptedCurrency() {
	    ProfileInformation instance = new ProfileInformation();
	    String accepted_currency = "dollar";
	    instance.setAccepted_currency("dollar");
	    String result = instance.getAccepted_currency();
	    assertEquals(accepted_currency, result);
	}
	
	/**
	 * testSetPublicName: Test of setPublicName method, of class ProfileInformation.
	 */
	@Test
	public void testSetPublicName() {
	    String public_name = "Stallone";
	    ProfileInformation instance = new ProfileInformation();
	    instance.setPublicName(public_name);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getPublicName(),public_name);
	}
	
	/**
	 * testGetPublicName: Test of getPublicName method, of class ProfileInformation.
	 */
	@Test
	public void testGetPublicName() {
	    ProfileInformation instance = new ProfileInformation();
	    String public_name = "Stallone";
	    instance.setPublicName("Stallone");
	    String result = instance.getPublicName();
	    assertEquals(public_name, result);
	}
	
	/**
	 * testSetProjectProfile: Test of setProjectProfile method, of class ProfileInformation.
	 */
	
	@Test
	public void testSetProjectProfile() {
		ProjectProfile project = new ProjectProfile();
		project.setLastmodifydate("12-12-1212");
        project.setPortfolioid("1234");
        project.setTitle("title");
        project.setDescription("preview_description");
        List<ProjectProfile> projects = new ArrayList<ProjectProfile>();
        projects.add(project);
	    
	    ProfileInformation instance = new ProfileInformation();
	    instance.setProjectProfile(projects);
	    assertEquals(instance.getProjectProfile(),projects);
	}
	
	/**
	 * testGetProjectProfile: Test of getProjectProfile method, of class ProfileInformation.
	 */
	
	@Test
	public void testGetProjectProfile() {
	    ProjectProfile project = new ProjectProfile();
	    project.setLastmodifydate("12-12-1212");
        project.setPortfolioid("1234");
        project.setTitle("title");
        project.setDescription("preview_description");
        List<ProjectProfile> projects = new ArrayList<ProjectProfile>();
        projects.add(project);
        
        ProjectProfile project1 = new ProjectProfile();
        project1.setLastmodifydate("12-12-1212");
        project1.setPortfolioid("1234");
        project1.setTitle("title");
        project1.setDescription("preview_description");
        List<ProjectProfile> projects1 = new ArrayList<ProjectProfile>();
        projects1.add(project1);
        
        ProfileInformation instance = new ProfileInformation();
        instance.setProjectProfile(projects1);
	    List<ProjectProfile>  result= instance.getProjectProfile();
	    assertEquals(projects1, result);
	}
	
	
}
