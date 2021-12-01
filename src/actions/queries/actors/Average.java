package actions.queries.actors;

import actor.Actor;
import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Average extends QueryActor {
    public Average(final String objectType, final String sortType,
                   final String criteria, final int number) {
        super(objectType, sortType, criteria, number);
    }

    /**
     * Public method that store in a map all video's ratings by their name
     * @return The new formed map
     */
    public Map<String, Double> getAllRatings() {
        Map<String, Double> ratingsMap = new HashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            double finalRating = getMovieRating(movie);
            if (finalRating != 0) {
                ratingsMap.put(movie.getTitle(), finalRating);
            }
        }

        for (Serial serial: Database.getDatabase().getSerials()) {
            double finalRating = getSerialRating(serial);
            if (finalRating != 0) {
                ratingsMap.put(serial.getTitle(), finalRating);
            }
        }

        return ratingsMap;
    }

    @Override
    public StringBuilder applyQuery() {
        Map<String, Double> ratingsMap = getAllRatings();

        Map<String, Double> ratedVideoPlayed = new HashMap<>();

        for (Actor actor : Database.getDatabase().getActors()) {
            int contor = 0;
            double sumOfRatings = 0;
            for (String video : actor.getFilmography()) {
                if (ratingsMap.containsKey(video)) {
                    contor++;
                    sumOfRatings += ratingsMap.get(video);
                }
            }
            if (contor != 0) {
                double finalRating = sumOfRatings / contor;
                ratedVideoPlayed.put(actor.getName(), finalRating);
            }
        }

        List<Map.Entry<String, Double>> sortedList = mysortDouble(ratedVideoPlayed,
                this.getSortType());

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
