package actions.queries.actors;

import actor.Actor;
import entertainment.Database;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Arrays;

import static common.Constants.WORDS_FILTER;

public final class FilterDescription extends QueryActor {
    private final List<List<String>> filters;

    public FilterDescription(final String objectType, final String sortType,
                             final String criteria, final int number,
                             final List<List<String>> filters) {
        super(objectType, sortType, criteria, number);
        this.filters = filters;
    }

    /**
     * Method for finding the actors matching all the filter words case-insensitive
     * in their career description.
     * @return A list of actor's names
     */
    public List<String> filterActors() {
        List<String> filteredActors = new ArrayList<>();

        for (Actor actor : Database.getDatabase().getActors()) {
            int count = 0;
            for (String word : this.filters.get(WORDS_FILTER)) {
                boolean isContained = Arrays.asList(actor.getCareerDescription()
                        .toLowerCase(Locale.ROOT).split("\\W+"))
                        .contains(word.toLowerCase(Locale.ROOT));

                if (isContained) {
                    count++;
                }

                if (count == this.filters.get(WORDS_FILTER).size()) {
                    filteredActors.add(actor.getName());
                }
            }
        }

        return filteredActors;
    }

    @Override
    public StringBuilder applyQuery() {
        List<String> filteredActors = filterActors();

        if (this.getSortType().equals("asc")) {
            filteredActors.sort(String::compareTo);
        } else {
            filteredActors.sort(Comparator.reverseOrder());
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Query result: [");

        int number = Math.min(this.getNumber(), filteredActors.size());
        for (int i = 0; i < number; i++) {
            if (i != (number - 1)) {
                stringBuilder.append(filteredActors.get(i)).append(", ");
            } else {
                stringBuilder.append(filteredActors.get(i));
            }
        }
        stringBuilder.append("]");

        return stringBuilder;
    }

    public List<List<String>> getFilters() {
        return filters;
    }
}
