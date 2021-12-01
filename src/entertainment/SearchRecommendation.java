package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;;

public class SearchRecommendation extends Recommandation {
    private String genre;

    public SearchRecommendation(final String subscriptionType, final String targetUser,
                                final String genre) {
        super(subscriptionType, targetUser);
        this.genre = genre;
    }

    public Map<String, Double> getNonViewed(String genre, User user) {
        Map<String, Double> unseenVideos = new HashMap<>();

        for (Movie movie : Database.getDatabase().getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                if (movie.getGenres().contains(genre)) {
                    unseenVideos.put(movie.getTitle(), calculateMovieRating(movie));
                }
            }
        }

        for (Serial serial : Database.getDatabase().getSerials()) {
            if (!user.getHistory().containsKey(serial.getTitle())) {
                if (serial.getGenres().contains(genre)) {
                    unseenVideos.put(serial.getTitle(), calculateSerialRating(serial));
                }
            }
        }

        return unseenVideos;
    }

    public List<Map.Entry<String, Double>> mysort(Map<String, Double> map) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue().compareTo(o2.getValue()) == 0) {
                return o1.getKey().compareTo(o2.getKey());
            } else return o1.getValue().compareTo(o2.getValue());

        });

        return list;
    }
    @Override
    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SearchRecommendation ");

        if (user.getSubscriptionType().equals("PREMIUM")) {
            Map<String, Double> filteredNonViewed = getNonViewed(this.getGenre(), user);

            List<Map.Entry<String, Double>> sortedNonViewed = mysort(filteredNonViewed);

            if (sortedNonViewed.size() == 0) {
                stringBuilder.append("cannot be applied!");
            } else {
                stringBuilder.append("result: [");
                for (int i = 0; i < sortedNonViewed.size(); i++) {
                    if (i != (sortedNonViewed.size() - 1))
                        stringBuilder.append(sortedNonViewed.get(i).getKey()).append(", ");
                    else
                        stringBuilder.append(sortedNonViewed.get(sortedNonViewed.size() - 1).getKey()).append("]");
                }
            }
        }
        return stringBuilder;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
