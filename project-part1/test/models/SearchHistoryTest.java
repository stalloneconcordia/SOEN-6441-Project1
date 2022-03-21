package models;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** 
*The class SearchHistoryTest
*@author Group
*/
public class SearchHistoryTest {
	
	/**
	 * testHistory: Test of addToHistory, addToHistoryPro method, of class SearchHistory.
	 */@Test
    public void testHistory() {
        SearchHistory searchHistory = new SearchHistory();
        assertTrue(searchHistory.getHistory("session_1").isEmpty());

        for (int i = 1; i <= 10; i++) {
            String query = "query" + i;
            SearchResult result = new SearchResult(query,
                    Arrays.asList(new Projects("null", "null", "null","null", "null", (HashMap<String, Integer>) Collections.<String, Integer>emptyHashMap())));
            searchHistory.addToHistory("session_1", result);
            List<SearchResult> history = searchHistory.getHistory("session_1");
            assertEquals(i, history.size());
        }
        assertEquals(10, searchHistory.getHistory("session_1").size());

    }
}