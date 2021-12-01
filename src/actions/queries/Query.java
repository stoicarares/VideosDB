package actions.queries;

import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Query {
    private final String objectType;
    private final String sortType;
    private final String criteria;
    private final int number;

    public Query(final String objectType, final String sortType,
                 final String criteria, final int number) {
        this.objectType = objectType;
        this.sortType = sortType;
        this.criteria = criteria;
        this.number = number;
    }

    /**
     * Method for sorting maps containing integer values ascendent or descendent by two criterias:
     *  Firstly by their value;
     *  Secondly by their name if the values are the same;
     * @param map The stored pairs(Key, Value) going to be sorted
     * @param sortingType deciding the order entries will be sorted
     * @return The list of sorted etries by the given type and criteria
     */
    public List<Map.Entry<String, Integer>> mysort(final Map<String, Integer> map,
                                                   final String sortingType) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> {
            if (sortingType.equals("asc")) {
                if (o1.getValue().compareTo(o2.getValue()) == 0) {
                    return o1.getKey().compareTo(o2.getKey());
                } else {
                    return o1.getValue().compareTo(o2.getValue());
                }
            } else if (sortingType.equals("desc")) {
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

    /**
     * Method for sorting maps containing double values ascendent or descendent by two criterias:
     *  Firstly by their value;
     *  Secondly by their name if the values are the same;
     * @param map The stored pairs(Key, Value) going to be sorted
     * @param sortingType deciding the order entries will be sorted
     * @return The list of sorted etries by the given type and criterias
     */
    public List<Map.Entry<String, Double>> mysortDouble(final Map<String, Double> map,
                                                        final String sortingType) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
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

    /**
     * Method for calculating a movie's rating
     * @param movie The movie to calculate rating for
     * @return A double value meaning the rating
     */
    public double getMovieRating(final Movie movie) {
        double finalRating = 0;
        for (Double rating : movie.getRatings()) {
            finalRating += rating;
        }
        if (movie.getRatings().size() != 0) {
            finalRating /= movie.getRatings().size();
        }

        return finalRating;
    }

    /**
     * Method for calculating a serial's rating
     * @param serial The serial to calculate rating for
     * @return A double value meaning the rating
     */
    public double getSerialRating(final Serial serial) {
        double finalRating = 0;
        for (Season season : serial.getSeasons()) {
            double seasonRating = 0;
            for (Double rating : season.getRatings()) {
                seasonRating += rating;
            }
            if (seasonRating != 0) {
                finalRating += seasonRating / season.getRatings().size();
            }
        }
        if (serial.getNumberOfSeasons() != 0) {
            finalRating /= serial.getNumberOfSeasons();
        }

        return finalRating;
    }

    public final String getObjectType() {
        return objectType;
    }

    public final String getSortType() {
        return sortType;
    }

    public final String getCriteria() {
        return criteria;
    }

    public final int getNumber() {
        return number;
    }
}
