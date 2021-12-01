package actions.queries.actors;

import actor.Actor;
import entertainment.Database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.Utils.stringToAwards;
import static common.Constants.AWARDS_FILTER;

public final class Awards extends QueryActor {
    private final List<List<String>> filters;

    public Awards(final String objectType, final String sortType,
                  final String criteria, final int number,
                  final List<List<String>> filters) {
        super(objectType, sortType, criteria, number);
        this.filters = filters;
    }

    /**
     * Method that calculates the total actor's prizes
     * @param actor The target actor
     * @return The number of actor's prizes
     */
    public int getNumberOfAwards(final Actor actor) {
        return actor.getAwards().values().stream().reduce(0, Integer::sum);
    }
    /**
     * Method that verifies actors matching the filters and storing them in a map
     * @return The new map containing the actor's names and total prizes
     */
    public Map<String, Integer> checkAwards() {
        Map<String, Integer> awardsCount = new HashMap<>();

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

    @Override
    public StringBuilder applyQuery() {
        Map<String, Integer> awardsMap = checkAwards();

        List<Map.Entry<String, Integer>> sortedList = mysort(awardsMap, this.getSortType());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");

        int number = Math.min(this.getNumber(), sortedList.size());
        for (int i = 0; i < number; i++) {
            if (i != (number - 1)) {
                stringBuilder.append(sortedList.get(i).getKey()).append(", ");
            } else {
                stringBuilder.append(sortedList.get(i).getKey());
            }
        }
        stringBuilder.append("]");

        return stringBuilder;
    }
}
