package actor;

import entertainment.Database;
import entertainment.Query;
import entertainment.QueryUser;
import entertainment.User;

import java.util.*;

public class NumberOfRatings  extends QueryUser {

    public NumberOfRatings(final String objectType, final String sortType,
                           final String criteria, final int number) {
        super(objectType, sortType, criteria, number);
    }

    public List<Map.Entry<String, Integer>> mysort(Map<String, Integer> map, String sortType) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
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


    public StringBuilder applyQuery() {
//        ArrayList<User> sortedUsers = new ArrayList<>();

        Map<String, Integer> ratingsMap = new HashMap<>();
        for (User user : Database.getDatabase().getUsers()) {
            int numberOfRatings = user.getRatedMovies().size() + user.getRatedSerials().size();
            if (numberOfRatings != 0)
                ratingsMap.put(user.getUsername(), numberOfRatings);
        }
        List<Map.Entry<String, Integer>> sortedUsers = mysort(ratingsMap, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");

        int number = Math.min(this.getNumber(), sortedUsers.size());
//        System.out.println(sortedUsers.size() + " " + number);
        for (int i = 0; i < number; i++) {
            if (sortedUsers.get(i).getValue() != 0) {
                if (i != (number - 1))
                    stringBuilder.append(sortedUsers.get(i).getKey()).append(", ");
                else
                    stringBuilder.append(sortedUsers.get(i).getKey()).append("]");
            }
        }
//        stringBuilder.append("]");
//        System.out.println(stringBuilder.toString());
        return stringBuilder;
//        System.arraycopy(Database.getDatabase().getUsers(), 0, sortedUsers, 0, Database.getDatabase().getUsers().size());


    }
}
