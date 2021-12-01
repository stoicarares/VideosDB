package actions.recommendations.basic;

import entertainment.Database;
import entertainment.User;
import entertainment.Video;
import actions.recommendations.Recommandation;

public final class Standard extends Recommandation {
    public Standard(final String subscriptionType, final String targetUser) {
        super(subscriptionType, targetUser);
    }

    @Override
    public StringBuilder applyRecommendation() {
        User user = Database.getDatabase().findUserByName(this.getTargetUser());
        String notViewedVideo = null;

        for (Video video : Database.getDatabase().getVideos()) {
            if (!user.getHistory().containsKey(video.getTitle())) {
                notViewedVideo = video.getTitle();
                break;
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
