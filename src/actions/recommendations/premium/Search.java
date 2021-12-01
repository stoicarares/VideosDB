package actions.recommendations.premium;

import actions.recommendations.Recommandation;
import entertainment.Database;
import entertainment.Movie;
import entertainment.Serial;
import entertainment.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;;

public final class Search extends Recommandation {
    private final String genre;

    public Search(final String subscriptionType, final String targetUser,
                  final String genre) {
        super(subscriptionType, targetUser);
        this.genre = genre;
    }

    /**
     * Method for forming a map with all unseen videos, in Database order, by the given user
     * and their ratings.
     * @param user The user to make recommendation for
     * @return The new formed map
     */
    public Map<String, Double> getNonViewedRated(final User user) {
        Map<String, Double> unseenVideos = new HashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                if (movie.getGenres().contains(this.getGenre())) {
                    unseenVideos.put(movie.getTitle(), calculateMovieRating(movie));
                }
            }
        }

        for (Serial serial : Database.getDatabase().getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                if (serial.getGenres().contains(this.getGenre())) {
                    unseenVideos.put(serial.getTitle(), calculateSerialRating(serial));
                }
            }
        }

        return unseenVideos;
    }

    /**
     * Method for sorting map entries ascendant order firstly by their value, secondly by their key
     * @param map Input map to be sorted
     * @return A sorted list of entries
     */
    public List<Map.Entry<String, Double>> mysort(final Map<String, Double> map) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue().compareTo(o2.getValue()) == 0) {
                return o1.getKey().compareTo(o2.getKey());
            } else  {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        return list;
    }

    @Override
    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SearchRecommendation ");

        if (user.getSubscriptionType().equals("PREMIUM")) {
            Map<String, Double> filteredNonViewed = getNonViewedRated(user);
            List<Map.Entry<String, Double>> sortedNonViewed = mysort(filteredNonViewed);

            if (sortedNonViewed.size() == 0) {
                stringBuilder.append("cannot be applied!");
            } else {
                stringBuilder.append("result: [");
                for (int i = 0; i < sortedNonViewed.size(); i++) {
                    if (i != (sortedNonViewed.size() - 1)) {
                        stringBuilder.append(sortedNonViewed.get(i).getKey()).append(", ");
                    } else {
                        stringBuilder.append(sortedNonViewed.get(sortedNonViewed.size() - 1)
                                .getKey()).append("]");
                    }
                }
            }
        }
        return stringBuilder;
    }

    public String getGenre() {
        return genre;
    }
}
