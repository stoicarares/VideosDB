package actions.recommendations.premium;

import entertainment.Database;
import entertainment.User;
import entertainment.Video;
import actions.recommendations.Recommandation;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public final class Popular extends Recommandation {
    public Popular(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }

    /**
     * Method for sorting a map descendant order by its values.
     * @param map Map to be sorted
     * @return A list containing map entries sorted
     */
    public List<Map.Entry<String, Integer>> mysort(final Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list;
    }

    /**
     * Method for forming a map containing all the genres of all videos from the Database in order
     * and an integer value meaning the number of video's views.
     * @return The new formed map.
     */
    public Map<String, Integer> getPopularGenres() {
        Map<String, Integer> popularGenres = new HashMap<>();

        for (Video video : Database.getDatabase().getVideos()) {
            for (String genre : video.getGenres()) {
                popularGenres.put(genre, 0);
            }
        }

        for (User userIt : Database.getDatabase().getUsers()) {
            for (Map.Entry<String, Integer> viewedVideo : userIt.getHistory().entrySet()) {
                Video video = Database.getDatabase().findMovieByName(viewedVideo.getKey());
                if (video == null) {
                    video = Database.getDatabase().findSerialByName(viewedVideo.getKey());
                }
                for (String genre : video.getGenres()) {
                    popularGenres.put(genre, popularGenres.get(genre) + viewedVideo.getValue());
                }
            }
        }

        return popularGenres;
    }

    /**
     * Method for finding the first video that hasn't been seen yet by the given user,
     * taking the genres from the most to least popular
     * @param sortedGenres A list of entries sorted descendant
     * @param user The target user to make recommendation on
     * @return A sting meaning the name of the found video
     */
    public String getSearchedVideo(final List<Map.Entry<String, Integer>> sortedGenres,
                                   final User user) {
        for (Map.Entry<String, Integer> sortedGenre : sortedGenres) {
            String searchedGenre = sortedGenre.getKey();
            for (Video video : Database.getDatabase().getVideos()) {
                if (!user.getHistory().containsKey(video.getTitle())) {
                    if (video.getGenres().contains(searchedGenre)) {
                        return video.getTitle();
                    }
                }
            }
        }

        return null;
    }

    @Override
    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PopularRecommendation ");

        if (user.getSubscriptionType().equals("PREMIUM")) {
            Map<String, Integer> popularGenres = getPopularGenres();
            List<Map.Entry<String, Integer>> sortedGenres = mysort(popularGenres);
            String searchedVideo = getSearchedVideo(sortedGenres, user);

            if (searchedVideo == null) {
                stringBuilder.append("cannot be applied!");
            } else {
                stringBuilder.append("result: ").append(searchedVideo);
            }

        } else {
            stringBuilder.append("cannot be applied!");
        }

        return stringBuilder;
    }
}
