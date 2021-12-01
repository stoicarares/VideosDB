package actions.queries.videos;

import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public final class MostViewed extends QueryVideo {
    public MostViewed(final String objectType, final String sortType,
                      final String criteria, final int number, final List<List<String>> filters) {
        super(objectType, sortType, criteria, number, filters);
    }

    /**
     * Method for getting how many times a video was viewed b all users
     * @param name The video's name is searched in users's history
     * @return An integer meaning total number of views
     */
    public int getNumberOfViews(final String name) {
        int views = 0;
        for (User user : Database.getDatabase().getUsers()) {
            if (user.getHistory().containsKey(name)) {
                views += user.getHistory().get(name);
            }
        }

        return views;
    }

    /**
     * Method for forming a map that will contain the movies's names matching the filters and
     * the integer value of their total number of views
     * @return The new formed map
     */
    public Map<String, Integer> getMostViewedMovies() {
        Map<String, Integer> mostViewedMovies = new HashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            if (fitsFilters(movie.getYear(), movie.getGenres())) {
                if (getNumberOfViews(movie.getTitle()) != 0) {
                    mostViewedMovies.put(movie.getTitle(), getNumberOfViews(movie.getTitle()));
                }
            }
        }

        return mostViewedMovies;
    }

    /**
     * Method for forming a map that will contain the serials's names matching the filters and
     * the integer value of their total number of views
     * @return The new formed map
     */
    public Map<String, Integer> getMostViewedSerials() {
        Map<String, Integer> mostViewedSerials = new HashMap<>();

        for (Serial serial: Database.getDatabase().getSerials()) {
            if (fitsFilters(serial.getYear(), serial.getGenres())) {
                if (getNumberOfViews(serial.getTitle()) != 0) {
                    mostViewedSerials.put(serial.getTitle(), getNumberOfViews(serial.getTitle()));
                }
            }
        }

        return mostViewedSerials;
    }

    @Override
    public StringBuilder applyQuery() {
        Map<String, Integer> mostViewedMap = null;

        if (this.getObjectType().equals("movies")) {
            mostViewedMap = getMostViewedMovies();
        } else if (this.getObjectType().equals("shows")) {
            mostViewedMap = getMostViewedSerials();
        }

        assert mostViewedMap != null;
        List<Map.Entry<String, Integer>> sortedList = mysort(mostViewedMap, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");

        int number = Math.min(this.getNumber(), sortedList.size());
        for (int i = 0; i < number; i++) {
            if (i != (number - 1)) {
                stringBuilder.append(sortedList.get(i).getKey()).append(", ");
            } else {
                stringBuilder.append(sortedList.get(i).getKey());
            }

        }
        stringBuilder.append("]");

        return stringBuilder;
    }
}
