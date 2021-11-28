package entertainment;

import java.util.ArrayList;

public abstract class Show {
    private String title;
    private int year;
    private ArrayList<String> genres;

    public Show(final String title, final int year, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }
}
