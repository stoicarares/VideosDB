package entertainment;

import java.util.*;

public class BestUnseenRecommendation extends Recommandation {
    public BestUnseenRecommendation(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }

    @Override
    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());
        Map <String, Double> unseenMap = new LinkedHashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                unseenMap.put(movie.getTitle(), calculateMovieRating(movie));
            }
        }
        for (Serial serial : Database.getDatabase().getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                unseenMap.put(serial.getTitle(), calculateSerialRating(serial));
            }
        }

//        double biggestRating = Math.max(unseenMap.values());
        double biggestRating = 0;
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
