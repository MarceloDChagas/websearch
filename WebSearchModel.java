import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Perform "web search" (from a  file), notify the interested observers of each query.
 */

public interface IWebSearchModel {
    void pretendToSearch();
    void addQueryObserver(WebSearchModel.QueryObserver queryObserver, WebSearchModel.QueryFilter queryFilter);
}

public class WebSearchModel implements IWebSearchModel {
    private final File sourceFile;
    private final List<ObserverFilterPair> observers = new ArrayList<>();

    public interface QueryObserver {
        void onQuery(String query);
    }

    public interface QueryFilter {
        boolean shouldNotify(String query);
    }

    private static class ObserverFilterPair {
        final QueryObserver observer;
        final QueryFilter filter;

        ObserverFilterPair(QueryObserver observer, QueryFilter filter) {
            this.observer = observer;
            this.filter = filter;
        }
    }

    public WebSearchModel(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Simulates web search by reading queries from file and notifying observers.
     * Delegates file reading responsibility to FileQueryReader utility.
     */
    public void pretendToSearch() {
        try {
            // Delegate file reading to FileQueryReader and focus only on query processing
            FileQueryReader.readAndProcessQueries(sourceFile, this::processQuery);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes a single query by notifying all interested observers.
     * This method focuses solely on the notification logic.
     * @param query The query to process
     */
    private void processQuery(String query) {
        notifyAllObservers(query);
    }

    /**
     * Registers an observer with its associated filter.
     * Follows Single Responsibility Principle - only handles observer management.
     * @param queryObserver The observer to be notified
     * @param queryFilter The filter that determines when to notify the observer
     */
    public void addQueryObserver(QueryObserver queryObserver, QueryFilter queryFilter) {
        observers.add(new ObserverFilterPair(queryObserver, queryFilter));
    }

    /**
     * Notifies all registered observers about a query, but only if their filters approve.
     * This method focuses solely on the notification logic with filtering.
     * @param query The query to potentially notify observers about
     */
    private void notifyAllObservers(String query) {
        for (ObserverFilterPair pair : observers) {
            if (pair.filter.shouldNotify(query)) {
                pair.observer.onQuery(query);
            }
        }
    }
}
