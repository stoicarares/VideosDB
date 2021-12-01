package actions.queries.users;

import entertainment.Database;
import entertainment.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NumberOfRatings extends QueryUser {

    public NumberOfRatings(final String objectType, final String sortType,
                           final String criteria, final int number) {
        super(objectType, sortType, criteria, number);
    }

    @Override
    public StringBuilder applyQuery() {

        Map<String, Integer> ratingsMap = new HashMap<>();
        for (User user : Database.getDatabase().getUsers()) {
            int numberOfRatings = user.getRatedMovies().size() + user.getRatedSerials().size();
            if (numberOfRatings != 0) {
                ratingsMap.put(user.getUsername(), numberOfRatings);
            }
        }
        List<Map.Entry<String, Integer>> sortedUsers = mysort(ratingsMap, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");

        int number = Math.min(this.getNumber(), sortedUsers.size());
        for (int i = 0; i < number; i++) {
            if (sortedUsers.get(i).getValue() != 0) {
                if (i != (number - 1)) {
                    stringBuilder.append(sortedUsers.get(i).getKey()).append(", ");
                } else {
                    stringBuilder.append(sortedUsers.get(i).getKey()).append("]");
                }
            }
        }

        return stringBuilder;
    }
}
