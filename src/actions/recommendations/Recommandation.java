package actions.recommendations;

import entertainment.Movie;
import entertainment.Season;
import entertainment.Serial;

public abstract class Recommandation {
    private final String subscriptionType;
    private final String targetUser;

    public Recommandation(final String type, final String targetUser) {
        this.subscriptionType = type;
        this.targetUser = targetUser;
    }

    /**
     * Abstract method for apply the given recommendation.
     * @return The string resulted due to the action
     */
    public abstract StringBuilder applyRecommendation();

    /**
     * Method for calculating a movie's rating
     * @param movie The movie to calculate rating for
     * @return A double value meaning the rating
     */
    public double calculateMovieRating(final Movie movie) {
        double finalRating = 0;
        double sum = 0;
        for (Double rating : movie.getRatings()) {
            sum += rating;
        }

        if (movie.getRatings().size() != 0) {
            finalRating = sum / movie.getRatings().size();
        }

        return finalRating;
    }

    /**
     * Method for calculating a serial's rating
     * @param serial The serial to calculate rating for
     * @return A double value meaning the rating
     */
    public double calculateSerialRating(final Serial serial) {
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

    public final String getType() {
        return subscriptionType;
    }

    public final String getTargetUser() {
        return targetUser;
    }
}
