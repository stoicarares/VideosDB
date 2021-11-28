package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Show {
    private int duration;
    private List<Double> ratings =  new ArrayList<>();

    public Movie(final String title, final int year,
                 final ArrayList<String> genres, final int duration) {
        super(title, year, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }

    public void setRatings(List<Double> ratings) {
        this.ratings = ratings;
    }
}
