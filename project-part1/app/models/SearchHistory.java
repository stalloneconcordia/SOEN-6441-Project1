package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Swapnil,Stallone,Saumya,Esha
 */
public class SearchHistory {
    /** The maximum history should be showed on the search results */
    private static final int MAX_HISTORY = 10;

    /** a map from session_id to its search history */
    private final Map<String, List<SearchResult>> sessions = new HashMap<>();
    private final Map<String, List<SearchProfile>> sessions_profile = new HashMap<>();

    /**
     * @author Swapnil,Stallone,Saumya,Esha
     * The method addToHistory, add the search result with its sessionId to the history
     * @param sessionId takes a String session id as an arguement
     * @param result takes an object of SearchResult class as an arguement
     */
    public synchronized void addToHistory(String sessionId, SearchResult result) {
        sessions.compute(sessionId, (k, curr) -> {
            if (curr == null) {
                curr = new ArrayList<>();
            } else if (curr.size() >= MAX_HISTORY) {
                curr = new ArrayList<>(curr.subList(0, MAX_HISTORY - 1));
            }
            curr.add(0, result);
            return curr;
        });
    }
    /**
     * @author Swapnil,Stallone,Saumya,Esha
     * The method addToHistory, add the search result with its sessionId to the history
     * @param sessionId takes a String session id as an arguement
     * @param searchProfile takes an object of SearchProfile class as an arguement
     */
    public synchronized void addToHistoryPro(String sessionId, SearchProfile searchProfile) {
        sessions_profile.compute(sessionId, (k, curr) -> {
            if (curr == null) {
                curr = new ArrayList<>();
            } else if (curr.size() >= MAX_HISTORY) {
                curr = new ArrayList<>(curr.subList(0, MAX_HISTORY - 1));
            }
            curr.add(0, searchProfile);
            return curr;
        });
    }

    /**
     * @author Swapnil,Stallone,Saumya,Esha
     * The method getHistory
     * @param sessionId takes a String session id as an arguement
     * @return sessions
     */
    public synchronized List<SearchResult> getHistory(String sessionId) {
        return sessions.getOrDefault(sessionId, new ArrayList<>());
    }
}
