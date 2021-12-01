package actions.queries.videos;

import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.Season;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Longest extends QueryVideo {
    public Longest(final String objectType, final String sortType,
                   final String criteria, final int number,
                   final List<List<String>> filters) {
        super(objectType, sortType, criteria, number, filters);
    }

    /**
     * Method for forming a map containg non-zero movie's durations that match given filters
     * @return A map containing movie names and their integer duration
     */
    public Map<String, Integer> getMoviesDurations() {
        Map<String, Integer> longestMovies = new HashMap<>();
        for (Movie movie : Database.getDatabase().getMovies()) {
            if (fitsFilters(movie.getYear(), movie.getGenres())) {
                if (movie.getDuration() != 0) {
                    longestMovies.put(movie.getTitle(), movie.getDuration());
                }
            }
        }

        return longestMovies;
    }

    /**
     * Method for forming a map containg non-zero serial's durations that match given filters
     * @return A map containing serial names and their integer duration
     */
    public Map<String, Integer> getSerialsDurations() {
        Map<String, Integer> longestSerials = new HashMap<>();

        for (Serial serial: Database.getDatabase().getSerials()) {
            if (fitsFilters(serial.getYear(), serial.getGenres())) {
                int duration = 0;
                for (Season season : serial.getSeasons()) {
                    duration += season.getDuration();
                }
                if (duration != 0) {
                    longestSerials.put(serial.getTitle(), duration);
                }
            }
        }

        return longestSerials;
    }

    @Override
    public StringBuilder applyQuery() {
        Map<String, Integer> longestMap = null;

        if (this.getObjectType().equals("movies")) {
            longestMap = getMoviesDurations();
        } else if (this.getObjectType().equals("shows")) {
            longestMap = getSerialsDurations();
        }

        assert longestMap != null;
        List<Map.Entry<String, Integer>> sortedList = mysort(longestMap, this.getSortType());

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
