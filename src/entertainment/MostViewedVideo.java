package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostViewedVideo extends QueryVideo {
    public MostViewedVideo(final String objectType, final String sortType,
                         final String criteria, final int number, final List<List<String>> filters) {
        super(objectType, sortType, criteria, number, filters);
    }

    public int getNumberOfViews(String name) {
        int views = 0;
        for (User user : Database.getDatabase().getUsers()) {
            if (user.getHistory().containsKey(name))
                views += user.getHistory().get(name);
        }

        return views;
    }

    @Override
    public StringBuilder applyQuery() {
        Map<String, Integer> sortedMap = new HashMap<>();

        if (this.getObjectType().equals("movies")) {
            for (Movie movie : Database.getDatabase().getMovies()) {
                if (fitsFilters(movie.getYear(), movie.getGenres()))
                    if (getNumberOfViews(movie.getTitle()) != 0)
                        sortedMap.put(movie.getTitle(), getNumberOfViews(movie.getTitle()));
            }
        } else if (this.getObjectType().equals("shows")) {
            for (Serial serial: Database.getDatabase().getSerials()) {
                if (fitsFilters(serial.getYear(), serial.getGenres()))
                    if (getNumberOfViews(serial.getTitle()) != 0)
                        sortedMap.put(serial.getTitle(), getNumberOfViews(serial.getTitle()));
            }
        }


        List<Map.Entry<String, Integer>> sortedList = mysort(sortedMap, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");
//        for (User user : sortedUsers) {
        int number = Math.min(this.getNumber(), sortedList.size());
        for (int i = 0; i < number; i++) {
            if (sortedList.get(i).getValue() != 0) {
                if (i != (number - 1))
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
