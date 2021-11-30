package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingVideo extends QueryVideo {

    public RatingVideo(final String objectType, final String sortType,
                       final String criteria, final int number,
                       final List<List<String>> filters) {
        super(objectType, sortType, criteria, number, filters);
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
        Map<String, Double> sortedMap = new HashMap<>();

        if (this.getObjectType().equals("movies")) {
            for (Movie movie : Database.getDatabase().getMovies()) {
                if (fitsFilters(movie.getYear(), movie.getGenres())) {
                    double finalRating = 0;
                    for (Double rating : movie.getRatings()) {
                        finalRating += rating;
                    }
                    if (movie.getRatings().size() != 0)
                        finalRating /= movie.getRatings().size();
                    if (finalRating != 0)
                        sortedMap.put(movie.getTitle(), finalRating);
                }
            }
        } else if (this.getObjectType().equals("shows")) {
            for (Serial serial: Database.getDatabase().getSerials()) {
                if (fitsFilters(serial.getYear(), serial.getGenres())) {
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
                        sortedMap.put(serial.getTitle(), finalRating);
                }
            }
        }

//        System.out.println(sortedMap.toString());
        List<Map.Entry<String, Double>> sortedList = mysortDouble(sortedMap, this.getSortType());

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
