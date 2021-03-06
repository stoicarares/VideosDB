package entertainment;

import java.util.ArrayList;

public final class Serial extends Video {
    private final int numberOfSeasons;
    private final ArrayList<String> cast;
    private final ArrayList<Season> seasons;

    public Serial(final String title, final int year,
                  final ArrayList<String> genres,
                  final int numberOfSeasons, final ArrayList<String> cast,
                  final ArrayList<Season> seasons) {
        super(title, year, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.cast = cast;
        this.seasons = seasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }
}
