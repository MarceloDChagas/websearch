/**
 * Watches the search queries
 */
public class Snooper {
    private final WebSearchModel model;

    public Snooper(WebSearchModel model) {
        this.model = model;

        // Observer 1: Prints "Oh Yes! <query>" when query contains 'friend' (case insensitive)
        model.addQueryObserver(
            new WebSearchModel.QueryObserver() {
                @Override
                public void onQuery(String query) {
                    System.out.println("Oh Yes! " + query);
                }
            },
            new WebSearchModel.QueryFilter() {
                @Override
                public boolean shouldNotify(String query) {
                    return query.toLowerCase().contains("friend");
                }
            }
        );

        // Observer 2: Prints "So long <query>" when query has more than 60 characters
        model.addQueryObserver(
            new WebSearchModel.QueryObserver() {
                @Override
                public void onQuery(String query) {
                    System.out.println("So long " + query);
                }
            },
            new WebSearchModel.QueryFilter() {
                @Override
                public boolean shouldNotify(String query) {
                    return query.length() > 60;
                }
            }
        );
    }
}
