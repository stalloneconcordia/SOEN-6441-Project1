package controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class SearchFormTest
 * @author Group
 *
 */
public class SearchFormTest {
	
	/*
	* testSetInput - test of Set Input method of Search form class 
	* 
	*/
	@Test
	public void testSetInput() {
	    String input = "python";
	    SearchForm instance = new SearchForm();
	    instance.setInput(input);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getInput(), input);
	}

	/**
	 * testGetInput - Test of get Input method, of class Search form class.
	 */
	@Test
	public void testGetInput() {
//	    System.out.println("getFlightNumber");
		SearchForm instance = new SearchForm();
	    String input = "python";
	    instance.setInput("python");
	    String result = instance.getInput();
	    assertEquals(input, result);
	}


}