package actions.recommendations.basic;

import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;
import actions.recommendations.Recommandation;

import java.util.LinkedHashMap;
import java.util.Map;

public final class BestUnseen extends Recommandation {
    public BestUnseen(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }

    /**
     * Void method that stores in the given map unseen movies by a user and
     * a double number meaning their rating.
     * @param targetUser The user to search unseen movies for
     * @param unseenMoviesRatings The linked map where to store in found key-value pairs by the
     *      *                             order they are in database.
     */
    public void getUnseenMovieRatings(final User targetUser,
                                      final LinkedHashMap<String, Double> unseenMoviesRatings) {
        for (Movie movie : Database.getDatabase().getMovies()) {
            if (!targetUser.getHistory().containsKey(movie.getTitle())) {
                unseenMoviesRatings.put(movie.getTitle(), calculateMovieRating(movie));
            }
        }
    }

    /**
     * Void method that stores in the given map unseen serials by a user and
     * a double number meaning their rating.
     * @param targetUser The user to search unseen serials for
     * @param unseenSerialsRatings The linked map where to store in found key-value pairs by the
     *                             order they are in database.
     */
    public void getUnseenSerialRatings(final User targetUser,
                                       final LinkedHashMap<String, Double> unseenSerialsRatings) {
        for (Serial serial : Database.getDatabase().getSerials()) {
            if (!targetUser.getHistory().containsKey(serial.getTitle())) {
                unseenSerialsRatings.put(serial.getTitle(), calculateSerialRating(serial));
            }
        }
    }

    @Override
    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());
        LinkedHashMap<String, Double> unseenMap = new LinkedHashMap<>();

        getUnseenMovieRatings(user, unseenMap);
        getUnseenSerialRatings(user, unseenMap);

        Map.Entry<String, Double> maxEntry = null;

        for (Map.Entry<String, Double> entry : unseenMap.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BestRatedUnseenRecommendation ");

        if (maxEntry == null) {
            stringBuilder.append("cannot be applied!");
        } else {
            stringBuilder.append("result: ").append(maxEntry.getKey());
        }

        return stringBuilder;
    }
}
