package actions.queries.videos;

import actions.queries.Query;

import java.util.ArrayList;
import java.util.List;

import static common.Constants.YEAR_FILTER;
import static common.Constants.GENRE_FILTER;
import static common.Constants.FIRST_FILTER;

public abstract class QueryVideo extends Query {
    private final List<List<String>> filters;

    public QueryVideo(final String objectType, final String sortType,
                      final String criteria, final int number,
                      final List<List<String>> filters) {
        super(objectType, sortType, criteria, number);
        this.filters = filters;
    }

    /**
     * Abstract method for apply the given query for videos.
     * @return The string resulted due to the action
     */
    public abstract StringBuilder applyQuery();

    /**
     * Method that verifies matching given year and genre filters
     * @param year The searching video's appearance year
     * @param genres The list of the searching video's genres
     * @return True or False depending on matching or not the filters
     */
    public boolean fitsFilters(final int year, final ArrayList<String> genres) {
        if (this.getFilters().get(YEAR_FILTER).get(FIRST_FILTER) == null
                && this.getFilters().get(GENRE_FILTER).get(FIRST_FILTER) == null) {
            return true;
        }

        int ok = 0;

        if (this.getFilters().get(YEAR_FILTER).get(FIRST_FILTER) != null) {
            if (year != Integer.parseInt(this.getFilters().get(YEAR_FILTER).get(FIRST_FILTER))) {
                return false;
            }
            ok = 1;
        }

        if (this.getFilters().get(GENRE_FILTER).get(FIRST_FILTER) == null && ok == 0) {
            return false;
        }

        if (this.getFilters().get(GENRE_FILTER).get(FIRST_FILTER) == null && ok == 1) {
            return true;
        }

        for (String genre : genres) {
            if (genre.equals(this.getFilters().get(GENRE_FILTER).get(FIRST_FILTER))) {
                return true;
            }
        }

        return false;
    }

    public final List<List<String>> getFilters() {
        return filters;
    }
}
