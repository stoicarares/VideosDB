package entertainment;

import java.util.*;

public class PopularRecommendation extends Recommandation {
    public PopularRecommendation(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }


    public List<Map.Entry<String, Integer>> mysort(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));

        return list;
    }

    public Map<String, Integer> getPopularGenres() {
        Map <String, Integer> popularGenres = new HashMap<>();
        for (Movie movie : Database.getDatabase().getMovies()) {
            for (String genre : movie.getGenres()) {
                popularGenres.put(genre, 0);
            }
        }
        for (Serial serial : Database.getDatabase().getSerials()) {
            for (String genre : serial.getGenres()) {
                popularGenres.put(genre, 0);
            }
        }

        for (User userIt : Database.getDatabase().getUsers()) {
            for (Map.Entry<String, Integer> viewedVideo : userIt.getHistory().entrySet()) {
                Show video = Database.getDatabase().findMovieByName(viewedVideo.getKey());
                if (video == null)
                    video = Database.getDatabase().findSerialByName(viewedVideo.getKey());
                for (String genre : video.getGenres()) {
                    popularGenres.put(genre, popularGenres.get(genre) + viewedVideo.getValue());
                }
            }
        }

        return popularGenres;
    }

    public String getSearchedVideo(List<Map.Entry<String, Integer>> sortedGenres, User user) {
        for (Map.Entry<String, Integer> sortedGenre : sortedGenres) {
            String searchedGenre = sortedGenre.getKey();
            for (Movie movie : Database.getDatabase().getMovies()) {
                if (!user.getHistory().containsKey(movie.getTitle())) {
                    if (movie.getGenres().contains(searchedGenre))
                        return movie.getTitle();
                }
            }
            for (Serial serial : Database.getDatabase().getSerials()) {
                if (!user.getHistory().containsKey(serial.getTitle())) {
                    if (serial.getGenres().contains(searchedGenre)) {
                        return serial.getTitle();
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

//        Map <String, Integer> map = getPopularGenres();

        if (user.getSubscriptionType().equals("PREMIUM")) {
            Map <String, Integer> popularGenres = getPopularGenres();

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
