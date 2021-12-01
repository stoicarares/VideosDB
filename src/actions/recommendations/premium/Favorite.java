package actions.recommendations.premium;

import entertainment.Database;
import entertainment.User;
import entertainment.Video;
import actions.recommendations.Recommandation;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Favorite extends Recommandation {
    public Favorite(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }

    /**
     * Method that forms a map containing all the videos contained in all user's favorite lists
     * in Database order, excluding the one to make the recommendation for and an integer meaning
     * the number of the appearances.
     * @param user The user to exclude searching in his favorites list
     * @return A LinkedHasMap with all unseen movies that match all users's favorite lists
     *                     stored in Database order
     */
    public Map<String, Integer> getFavoriteMovies(final User user) {
        Map<String, Integer> favoriteMovies = new LinkedHashMap<>();

        for (Video video : Database.getDatabase().getVideos()) {
            for (User userIt : Database.getDatabase().getUsers()) {
                if (!userIt.getUsername().equals(user.getUsername())) {
                    if (userIt.getFavoriteMovies().contains(video.getTitle())
                            && !user.getHistory().containsKey(video.getTitle())) {
                        if (!favoriteMovies.containsKey(video.getTitle())) {
                            favoriteMovies.put(video.getTitle(), 1);
                        } else {
                            favoriteMovies.put(video.getTitle(),
                                    favoriteMovies.get(video.getTitle()) + 1);
                        }
                    }
                }
            }
        }

        return favoriteMovies;
    }

    @Override
    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FavoriteRecommendation ");

        if (user.getSubscriptionType().equals("PREMIUM")) {
            Map<String, Integer> mostOnFavorite = getFavoriteMovies(user);
            Map.Entry<String, Integer> maxEntry = null;

            for (Map.Entry<String, Integer> entry : mostOnFavorite.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }

            if (maxEntry == null) {
                stringBuilder.append("cannot be applied!");
            } else {
                stringBuilder.append("result: ").append(maxEntry.getKey());
            }
        } else {
            stringBuilder.append("cannot be applied!");
        }

        return stringBuilder;
    }
}
