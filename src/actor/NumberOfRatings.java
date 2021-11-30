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

    public static void mysort(ArrayList<User> list, String sortType) {
        list.sort((o1, o2) -> {
            if (sortType.equals("asc")) {
                if (o1.getRatedMovies().size() - o2.getRatedMovies().size() == 0) {
                    return o1.getUsername().compareTo(o2.getUsername());
                } else {
                    return o1.getRatedMovies().size() - o2.getRatedMovies().size();
                }
            } else if (sortType.equals("desc")) {
                if (o2.getRatedMovies().size() - o1.getRatedMovies().size() == 0) {
                    return o2.getUsername().compareTo(o1.getUsername());
                } else {
                    return o2.getRatedMovies().size() - o1.getRatedMovies().size();
                }
            }
            return 0;
        });
    }


    public StringBuilder applyQuery() {
        ArrayList<User> sortedUsers = new ArrayList<>();

//        Deep copy
        for (User user : Database.getDatabase().getUsers()) {
            sortedUsers.add(new User(user));
        }
        mysort(sortedUsers, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");
//        for (User user : sortedUsers) {
        int number = Math.min(this.getNumber(), sortedUsers.size());
        for (int i = 0; i < number; i++) {
            if (sortedUsers.get(i).getRatedMovies().size() != 0) {
                if (i != sortedUsers.size() - 1)
                    stringBuilder.append(sortedUsers.get(i).getUsername()).append(", ");
                else
                    stringBuilder.append(sortedUsers.get(i).getUsername());
            }
        }
        stringBuilder.append("]");
//        System.out.println(stringBuilder.toString());
        return stringBuilder;
//        System.arraycopy(Database.getDatabase().getUsers(), 0, sortedUsers, 0, Database.getDatabase().getUsers().size());


    }
}
