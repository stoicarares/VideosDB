package entertainment;

import java.util.LinkedHashMap;
import java.util.Map;

public class FavoriteRecommendation extends Recommandation {
    public FavoriteRecommendation(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }

    public Map<String, Integer> getFavoriteMovies(User user) {
        Map<String, Integer> favoriteMovies = new LinkedHashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            for (User userIt : Database.getDatabase().getUsers()) {
                if (!userIt.getUsername().equals(user.getUsername())) {
                    if (userIt.getFavoriteMovies().contains(movie.getTitle()) &&
                            !user.getHistory().containsKey(movie.getTitle())) {
                        if (!favoriteMovies.containsKey(movie.getTitle())) {
                            favoriteMovies.put(movie.getTitle(), 1);
                        } else {
                            favoriteMovies.put(movie.getTitle(),
                                    favoriteMovies.get(movie.getTitle()) + 1);
                        }
                    }
                }
            }
        }

        for (Serial serial : Database.getDatabase().getSerials()) {
            for (User userIt : Database.getDatabase().getUsers()) {
                if (!userIt.getUsername().equals(user.getUsername())) {
                    if (userIt.getFavoriteMovies().contains(serial.getTitle()) &&
                            !user.getHistory().containsKey(serial.getTitle())) {
                        if (!favoriteMovies.containsKey(serial.getTitle())) {
                            favoriteMovies.put(serial.getTitle(), 1);
                        } else {
                            favoriteMovies.put(serial.getTitle(),
                                    favoriteMovies.get(serial.getTitle()) + 1);
                        }
                    }
                }
            }
        }
//        for (User userIt : Database.getDatabase().getUsers()) {
//            if (!user.getUsername().equals(userIt.getUsername())) {
//                for (String video : userIt.getFavoriteMovies()) {
//                    if (!user.getHistory().containsKey(video)) {
//                        if (!favoriteMovies.containsKey(video)) {
//                            favoriteMovies.put(video, 1);
//                        } else {
//                            favoriteMovies.put(video, favoriteMovies.get(video) + 1);
//                        }
//                    }
//                }
//            }
//        }
//
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
