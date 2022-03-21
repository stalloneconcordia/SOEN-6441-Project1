package models;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

/** 
*The class GlobalStatsTest
*@author Swapnil
*/
public class GlobalStatsTest {
	
	/**
	 * testgetSortedWordCounter: Test of setSortedWordCounter method, of class GlobalStats.
	 */
	@Test
	public void testgetSortedWordCounter() {
		Map<String, Integer> stats = new LinkedHashMap<>();
		stats.put("hi", 1);
		GlobalStats instance = new GlobalStats();
	    instance.setSortedWordCounter(stats);
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(instance.getSortedWordCounter(), stats);
	}
	
	/**
	 * testsetSortedWordCounter: Test of getSortedWordCounter method, of class GlobalStats.
	 */
	@Test
	public void testsetSortedWordCounter() {
		Map<String, Integer> stats = new LinkedHashMap<>();
		stats.put("hi", 1);
		GlobalStats instance = new GlobalStats();
		HashMap<String, Integer> stats1 = new HashMap<>();
		stats1.put("hi", 1);
	    instance.setSortedWordCounter(stats1);
	    Map<String, Integer> result = instance.getSortedWordCounter();
	    // TODO review the generated test code and remove the default call to fail.
	    assertEquals(result, stats);
	}

}
