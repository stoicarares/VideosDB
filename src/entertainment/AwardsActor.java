package entertainment;


import actor.Actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.stringToAwards;
import static common.Constants.AWARDS_FILTER;

public class AwardsActor extends QueryActor {
    private List<List<String>> filters;

    public AwardsActor(final String objectType, final String sortType,
                       final String criteria, final int number,
                       final List<List<String>> filters) {
        super(objectType, sortType, criteria, number);
        this.filters = filters;
    }

    public int getNumberOfAwards(Actor actor) {
        return actor.getAwards().values().stream().reduce(0, Integer::sum);
    }

    public Map<String, Integer> checkAwards() {
        Map <String, Integer> awardsCount = new HashMap<>();

        for (Actor actor : Database.getDatabase().getActors()) {
            int count = 0;
            for (String award : this.filters.get(AWARDS_FILTER)) {
                if (actor.getAwards().containsKey(stringToAwards(award))) {
                    count++;
                }
            }
            if (count == this.filters.get(AWARDS_FILTER).size()) {
                awardsCount.put(actor.getName(), getNumberOfAwards(actor));
            }
        }

        return awardsCount;
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

    @Override
    public StringBuilder applyQuery() {
        Map<String, Integer> awardsMap = checkAwards();
//        System.out.println(awardsMap.toString());
//        for (Actor actor : Database.getDatabase().getActors()) {
//            System.out.println(actor.getName() + "  -> " + getNumberOfAwards(actor));
//        }

        List<Map.Entry<String, Integer>> sortedList = mysort(awardsMap, this.getSortType());

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
