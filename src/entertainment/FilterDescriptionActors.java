package entertainment;

import actor.Actor;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static common.Constants.WORDS_FILTER;
public class FilterDescriptionActors extends QueryActor {
    private List<List<String>> filters;

    public FilterDescriptionActors(final String objectType, final String sortType,
                       final String criteria, final int number,
                       final List<List<String>> filters) {
        super(objectType, sortType, criteria, number);
        this.filters = filters;
    }

    public List<String> filterActors() {
        List<String> filteredActors = new ArrayList<>();

        for (Actor actor : Database.getDatabase().getActors()) {
            int count = 0;
            for (String word : this.filters.get(WORDS_FILTER)) {
                boolean isContained = Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE).matcher(actor.getCareerDescription()).find();
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
            if (i != filteredActors.size() - 1)
                stringBuilder.append(filteredActors.get(i)).append(", ");
            else
                stringBuilder.append(filteredActors.get(i));

        }
        stringBuilder.append("]");
//        System.out.println(stringBuilder.toString());
        return stringBuilder;
    }

    public List<List<String>> getFilters() {
        return filters;
    }

    public void setFilters(List<List<String>> filters) {
        this.filters = filters;
    }
}
