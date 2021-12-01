package entertainment;

public abstract class Recommandation {
    private String subscriptionType;
    private String targetUser;

    public Recommandation(final String type, final String targetUser) {
        this.subscriptionType = type;
        this.targetUser = targetUser;
    }

    public abstract StringBuilder applyRecommendation();

    public double calculateMovieRating(Movie movie) {
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

    public double calculateSerialRating(Serial serial) {
        double finalRating = 0;
        for (Season season : serial.getSeasons()) {
            double seasonRating = 0;
            for (Double rating : season.getRatings()) {
                seasonRating += rating;
            }
            if (seasonRating != 0)
                finalRating += seasonRating / season.getRatings().size();
        }
        if (serial.getNumberOfSeasons() != 0)
            finalRating /= serial.getNumberOfSeasons();

        return finalRating;
    }

    public String getType() {
        return subscriptionType;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setType(String type) {
        this.subscriptionType = type;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }
}
