package entertainment;

import java.util.ArrayList;

public final class Serial extends Show {
    private int numberOfSeasons;
    private ArrayList<String> cast;
    private ArrayList<Season> seasons;

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

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }
}
