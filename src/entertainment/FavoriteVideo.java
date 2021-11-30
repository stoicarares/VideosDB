package entertainment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteVideo extends QueryVideo {

    public FavoriteVideo(final String objectType, final String sortType,
                         final String criteria, final int number, final List<List<String>> filters) {
        super(objectType, sortType, criteria, number, filters);
    }

    public boolean isInFavorite(String name) {
        for (User user : Database.getDatabase().getUsers()) {
            if (user.getFavoriteMovies().contains(name))
                return true;
        }

        return false;
    }
    @Override
    public StringBuilder applyQuery() {
//        ArrayList<User> sortedUsers = new ArrayList<>();

        Map<String, Integer> sortedMap = new HashMap<>();

        if (this.getObjectType().equals("movies")) {
            for (Movie movie : Database.getDatabase().getMovies()) {
                if (fitsFilters(movie.getYear(), movie.getGenres()) && isInFavorite(movie.getTitle()))
                    if(!sortedMap.containsKey(movie.getTitle()))
                        sortedMap.put(movie.getTitle(), 1);
                    else
                        sortedMap.put(movie.getTitle(), sortedMap.get(movie.getTitle()) + 1);
            }
        } else if (this.getObjectType().equals("shows")) {
            for (Serial serial: Database.getDatabase().getSerials()) {
                if (fitsFilters(serial.getYear(), serial.getGenres()) && isInFavorite(serial.getTitle()))
                    if(!sortedMap.containsKey(serial.getTitle()))
                        sortedMap.put(serial.getTitle(), 1);
                    else
                        sortedMap.put(serial.getTitle(), sortedMap.get(serial.getTitle()) + 1);
            }
        }


        List<Map.Entry<String, Integer>> sortedList = mysort(sortedMap, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");
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
        return stringBuilder;
    }
}
