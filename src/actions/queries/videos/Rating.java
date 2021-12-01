package actions.queries.videos;

import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Rating extends QueryVideo {

    public Rating(final String objectType, final String sortType,
                  final String criteria, final int number,
                  final List<List<String>> filters) {
        super(objectType, sortType, criteria, number, filters);
    }

    /**
     * Method for forming a map that will contain all movies's names that match the filters and
     * a non-zero double value meaning their ratings
     * @return The new formed map
     */
    public Map<String, Double> getAllMoviesRatings() {
        Map<String, Double> movieRatings = new HashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            if (fitsFilters(movie.getYear(), movie.getGenres())) {
                double finalRating = getMovieRating(movie);
                if (finalRating != 0) {
                    movieRatings.put(movie.getTitle(), finalRating);
                }
            }
        }
        return movieRatings;
    }

    /**
     * Method for forming a map that will contain all serials's names that match the filters and
     * a non-zero double value meaning their ratings
     * @return The new formed map
     */
    public Map<String, Double> getAllSerialsRatings() {
        Map<String, Double> serialsRatings = new HashMap<>();

        for (Serial serial: Database.getDatabase().getSerials()) {
            if (fitsFilters(serial.getYear(), serial.getGenres())) {
                double finalRating = getSerialRating(serial);
                if (finalRating != 0) {
                    serialsRatings.put(serial.getTitle(), finalRating);
                }
            }
        }

        return serialsRatings;
    }

    @Override
    public StringBuilder applyQuery() {
        Map<String, Double> ratingsMap = null;

        if (this.getObjectType().equals("movies")) {
            ratingsMap = getAllMoviesRatings();
        } else if (this.getObjectType().equals("shows")) {
            ratingsMap = getAllSerialsRatings();
        }

        assert ratingsMap != null;
        List<Map.Entry<String, Double>> sortedList = mysortDouble(ratingsMap, this.getSortType());

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
