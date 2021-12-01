package actions.queries.videos;

import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Favorite extends QueryVideo {

    public Favorite(final String objectType, final String sortType,
                    final String criteria, final int number, final List<List<String>> filters) {
        super(objectType, sortType, criteria, number, filters);
    }

    /**
     * Method for calculating the appearances of a video in all user's favorite lists
     * @param videoName The video is being searched.
     * @return  An integer meaning the all appearances
     */
    public int getNumberOfAppearances(final String videoName) {
        int numberOfAppearances = 0;
        for (User user : Database.getDatabase().getUsers()) {
            if (user.getFavoriteMovies().contains(videoName)) {
                numberOfAppearances++;
            }
        }

        return numberOfAppearances;
    }

    /**
     * Method for storing in a map all movies from database that exist in user's favorite lists
     * and their non-zero number of appearances
     * @return A new formed
     */
    public Map<String, Integer> getFavoriteMovies() {
        Map<String, Integer> favoriteMovies = new HashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            if (fitsFilters(movie.getYear(), movie.getGenres())) {
                int numberOfAppearances = getNumberOfAppearances(movie.getTitle());
                if (numberOfAppearances != 0) {
                    if (!favoriteMovies.containsKey(movie.getTitle())) {
                        favoriteMovies.put(movie.getTitle(), numberOfAppearances);
                    } else {
                        favoriteMovies.put(movie.getTitle(), favoriteMovies.get(movie.getTitle())
                                + numberOfAppearances);
                    }
                }
            }
        }
        return favoriteMovies;
    }

    /**
     * Method for storing in a map all serials from database that exist in user's favorite lists
     * and their non-zero number of appearances
     * @return A new formed
     */
    public Map<String, Integer> getFavoriteSerials() {
        Map<String, Integer> favoriteSerials = new HashMap<>();

        for (Serial serial: Database.getDatabase().getSerials()) {
            if (fitsFilters(serial.getYear(), serial.getGenres())) {
                int numberOfAppearances = getNumberOfAppearances(serial.getTitle());
                if (numberOfAppearances != 0) {
                    if (!favoriteSerials.containsKey(serial.getTitle())) {
                        favoriteSerials.put(serial.getTitle(), numberOfAppearances);
                    } else {
                        favoriteSerials.put(serial.getTitle(),
                                favoriteSerials.get(serial.getTitle()) + numberOfAppearances);
                    }
                }
            }
        }

        return favoriteSerials;
    }

    @Override
    public StringBuilder applyQuery() {
        Map<String, Integer> favoritesMap = null;

        if (this.getObjectType().equals("movies")) {
            favoritesMap = getFavoriteMovies();
        } else if (this.getObjectType().equals("shows")) {
            favoritesMap = getFavoriteSerials();
        }

        assert favoritesMap != null;
        List<Map.Entry<String, Integer>> sortedList = mysort(favoritesMap, this.getSortType());

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
