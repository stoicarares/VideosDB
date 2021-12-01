package entertainment;

import java.util.ArrayList;

public class Video {
    private final String title;
    private final int year;
    private final ArrayList<String> genres;

    public Video(final String title, final int year, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.genres = genres;
    }

    /**
     * Getter for title
     * @return Title of the video
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for year
     * @return Apparition year of the movie
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter for genres list
     * @return The genres movie contains
     */
    public ArrayList<String> getGenres() {
        return genres;
    }
}
