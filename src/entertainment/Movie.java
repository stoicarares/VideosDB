package entertainment;

import java.util.ArrayList;
import java.util.List;

public final class Movie extends Video {
    private final int duration;
    private final List<Double> ratings =  new ArrayList<>();

    public Movie(final String title, final int year,
                 final ArrayList<String> genres, final int duration) {
        super(title, year, genres);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public List<Double> getRatings() {
        return ratings;
    }
}
