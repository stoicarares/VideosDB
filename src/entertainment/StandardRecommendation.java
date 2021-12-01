package entertainment;

public class StandardRecommendation extends Recommandation {
    public StandardRecommendation(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }

    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());
        String notViewedVideo = null;
        for (Movie movie : Database.getDatabase().getMovies()) {
            if (!user.getHistory().containsKey(movie.getTitle())) {
                notViewedVideo = movie.getTitle();
                break;
            }
        }
        if (notViewedVideo == null) {
            for (Serial serial : Database.getDatabase().getSerials()) {
                if (!user.getHistory().containsKey(serial.getTitle())) {
                    notViewedVideo = serial.getTitle();
                    break;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("StandardRecommendation ");

        if (notViewedVideo == null) {
            stringBuilder.append("cannot be applied!");
        } else {
            stringBuilder.append("result: ").append(notViewedVideo);
        }

        return stringBuilder;
    }
}
