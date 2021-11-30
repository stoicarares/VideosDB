package entertainment;

import actor.Actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AverageActor extends QueryActor {
    public AverageActor(final String objectType, final String sortType,
                      final String criteria, final int number) {
        super(objectType, sortType, criteria, number);
    }

    public Map<String, Double> getRatings() {
        Map<String, Double> ratingsMap = new HashMap<>();
        for (Movie movie : Database.getDatabase().getMovies()) {
            double finalRating = 0;
            for (Double rating : movie.getRatings()) {
                finalRating += rating;
            }
            if (movie.getRatings().size() != 0)
                finalRating /= movie.getRatings().size();
            if (finalRating != 0)
                ratingsMap.put(movie.getTitle(), finalRating);
        }
        for (Serial serial: Database.getDatabase().getSerials()) {
            double finalRating = 0;
            for (Season season : serial.getSeasons()) {
                double seasonRating = 0;
                for (Double rating : season.getRatings()) {
                    seasonRating += rating;
                }
                if (seasonRating != 0)
                    finalRating += seasonRating / season.getRatings().size();
            }
            if (serial.getNumberOfSeasons() != 0)
                finalRating /= serial.getNumberOfSeasons();
            if (finalRating != 0)
                ratingsMap.put(serial.getTitle(), finalRating);
        }

        return ratingsMap;
    }

    public List<Map.Entry<String, Double>> mysortDouble(Map<String, Double> map, String sortType) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            if (sortType.equals("asc")) {
                if (o1.getValue().compareTo(o2.getValue()) == 0) {
                    return o1.getKey().compareTo(o2.getKey());
                } else {
                    return o1.getValue().compareTo(o2.getValue());
                }
            } else if (sortType.equals("desc")) {
                if (o2.getValue().compareTo(o1.getValue()) == 0) {
                    return o2.getKey().compareTo(o1.getKey());
                } else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
            return 0;
        });

        return list;
    }

    @Override
    public StringBuilder applyQuery() {
        Map<String, Double> ratingsMap = getRatings();

        Map<String, Double> ratedVideoPlayed = new HashMap<>();

        for (Actor actor : Database.getDatabase().getActors()) {
            for (String video : actor.getFilmography()) {
                int contor = 0;
                double sumOfRatings = 0;
                if (ratingsMap.containsKey(video)) {
                    contor++;
                    sumOfRatings += ratingsMap.get(video);
                }
                if (contor != 0) {
                    double finalRating = sumOfRatings / contor;
                    ratedVideoPlayed.put(actor.getName(), finalRating);
                }
            }
        }

        List<Map.Entry<String, Double>> sortedList = mysortDouble(ratedVideoPlayed, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");
//        for (User user : sortedUsers) {
        int number = Math.min(this.getNumber(), sortedList.size());
        for (int i = 0; i < number; i++) {
            if (sortedList.get(i).getValue() != 0) {
                if (i != sortedList.size() - 1)
                    stringBuilder.append(sortedList.get(i).getKey()).append(", ");
                else
                    stringBuilder.append(sortedList.get(i).getKey());
            }
        }
        stringBuilder.append("]");
//        System.out.println(stringBuilder.toString());
        return stringBuilder;
    }
}
