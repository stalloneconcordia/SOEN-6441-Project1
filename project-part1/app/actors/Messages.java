package actors;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import model.Searchphraseresult;
import models.SearchResult;


public final class Messages {
    private final static Logger logger = LoggerFactory.getLogger("play");

    public static final class UserParentActorCreate {
        public final String id;

        /**
         * Create UserParentActor Message
         */
        public UserParentActorCreate(String id) {
            logger.info("Created actor with id" + id);
            this.id = requireNonNull(id);
        }

        @Override
        public String toString() {
            return "UserParentActorCreate(" + id + ")";
        }
    }
    /**
     * WatchSearchResults Message
     */
    public static final class WatchSearchResults {
        public final String query;

        public WatchSearchResults(String query) {
            this.query = requireNonNull(query);
        }

        @Override
        public String toString() {
            return "WatchSearchResults(" + query + ")";
        }
    }

    /**
     * UnwatchSearchResults Message
     */
    public static final class UnwatchSearchResults {
        public final String query;

        public UnwatchSearchResults(String query) {
            this.query = requireNonNull(query);
        }

        @Override
        public String toString() {
            return "UnwatchSearchResults(" + query + ")";
        }
    }
    /**
     * SearchResults according to the query param passed
     */
    public static final class SearchResult_actor {
        public final Set<SearchResult> searchResults;
        public final String query;

        public SearchResult_actor(Set<SearchResult> searchResults, String query) {
            this.searchResults = searchResults;
            this.query = query;
        }

        @Override
        public String toString() {
            return "RepoItem(" + query + ")";
        }
    }

    /**
     * RegisterActor Message
     */
    public static final class RegisterActor {
        @Override
        public String toString() {
            return "RegisterActor";
        }
    }

    /**
     * Override toString for the Messages class
     * @return string "Messages"
     */
    @Override
    public String toString() {
        return "Messages";
    }
}
