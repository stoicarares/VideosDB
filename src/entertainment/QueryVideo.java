package entertainment;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static common.Constants.YEAR_FILTER;
import static common.Constants.GENRE_FILTER;
import static common.Constants.FIRST_GENRE;

public abstract class QueryVideo extends Query {
    private List<List<String>> filters;

    public QueryVideo(final String objectType, final String sortType,
                         final String criteria, final int number, final List<List<String>> filters) {
        super(objectType, sortType, criteria, number);
        this.filters = filters;
    }


//    @Override
    public abstract StringBuilder applyQuery();

    public boolean fitsFilters(int year, ArrayList<String> genres) {
        int ok = 0;

        if (this.getFilters().get(YEAR_FILTER) != null &&
                Integer.parseInt(this.getFilters().get(YEAR_FILTER).get(FIRST_GENRE)) == year)
            ok  = 1;

        if (ok == 0)
            return false;

        if (this.getFilters().get(GENRE_FILTER) == null)
            return true;

        for (String genre : genres) {
            if (this.getFilters().get(GENRE_FILTER).get(FIRST_GENRE).equals(genre))
                return true;
        }

        return false;
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

    public List<List<String>> getFilters() {
        return filters;
    }

    public void setFilters(List<List<String>> filters) {
        this.filters = filters;
    }
}
