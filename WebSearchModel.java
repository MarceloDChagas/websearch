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

    public void pretendToSearch() {
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
            while ( true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                notifyAllObservers(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQueryObserver(QueryObserver queryObserver, QueryFilter queryFilter) {
        observers.add(new ObserverFilterPair(queryObserver, queryFilter));
    }

    private void notifyAllObservers(String line) {
        for (ObserverFilterPair pair : observers) {
            if (pair.filter.shouldNotify(line)) {
                pair.observer.onQuery(line);
            }
        }
    }
}
