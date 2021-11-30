package entertainment;

import java.util.ArrayList;
import java.util.Map;

public class User {
    private String username;
    private String subscriptionType;
    private Map<String, Integer> history;
    private ArrayList<String> favoriteMovies;
    private ArrayList<String> ratedMovies = new ArrayList<>();
    private ArrayList<String> ratedSerials = new ArrayList<>();

    public User(final String username,final String subscriptionType,
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

    public String getUsername() {return username;}

    public void setUsername(String username) { this.username = username;}

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public void setHistory(Map<String, Integer> history) {
        this.history = history;
    }

    public ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(ArrayList<String> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public ArrayList<String> getRatedMovies() { return ratedMovies; }

    public void setRatedMovies(ArrayList<String> ratedMovies) { this.ratedMovies = ratedMovies; }

    public ArrayList<String> getRatedSerials() { return ratedSerials; }

    public void setRatedSerials(ArrayList<String> ratedSerials) { this.ratedSerials = ratedSerials; }
}
