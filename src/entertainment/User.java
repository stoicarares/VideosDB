package entertainment;

import java.util.ArrayList;
import java.util.Map;

public final class User {
    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private ArrayList<String> ratedMovies = new ArrayList<>();
    private ArrayList<String> ratedSerials = new ArrayList<>();

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;
    }

    public User(final User user) {
        this.username = user.username;
        this.subscriptionType = user.subscriptionType;
        this.history = user.history;
        this.favoriteMovies = user.favoriteMovies;
        this.ratedMovies = user.ratedMovies;
        this.ratedSerials = user.ratedSerials;
    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public ArrayList<String> getRatedMovies() {
        return ratedMovies;
    }

    public ArrayList<String> getRatedSerials() {
        return ratedSerials;
    }
}
