package entertainment;

import actor.Actor;

import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.List;

public final class Database {
    private final List<Actor> actors = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Movie> movies = new ArrayList<>();
    private final List<Serial> serials = new ArrayList<>();
    private final List<Video> videos = new ArrayList<>();

    private static Database database = null;

    private Database() {

    }

    /**
     * Lazy instantiation of the singleton
     * @return The singleton instance
     */
    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    /**
     * Function for storing all the given actors in my database
     * @param inputActors The list of actors given on input
     */
    public void transferActors(final List<ActorInputData> inputActors) {
        this.actors.clear();
        for (ActorInputData actor:inputActors) {
            Actor newActor = new Actor(actor.getName(), actor.getCareerDescription(),
                                        actor.getFilmography(), actor.getAwards());
            this.actors.add(newActor);
        }
    }

    /**
     * Function for storing all the given users in my database
     * @param inputUsers The list of users given on input
     */
    public void transferUsers(final List<UserInputData> inputUsers) {
        this.users.clear();
        for (UserInputData user:inputUsers) {
            User newUser = new User(user.getUsername(), user.getSubscriptionType(),
                                    user.getHistory(), user.getFavoriteMovies());
            this.users.add(newUser);
        }
    }

    /**
     * Function for storing all the given movies in my database
     * @param inputMovies The list of movies given on input
     */
    public void transferMovies(final List<MovieInputData> inputMovies) {
        this.movies.clear();
        for (MovieInputData movie:inputMovies) {
            Movie newMovie = new Movie(movie.getTitle(), movie.getYear(), movie.getGenres(),
                    movie.getDuration());

            this.movies.add(newMovie);
        }
    }

    /**
     * Function for storing all the given serials in my database
     * @param inputSerials The list of serials given on input
     */
    public void transferSerials(final List<SerialInputData> inputSerials) {
        this.serials.clear();
        for (SerialInputData serial:inputSerials) {
            Serial newSerial = new Serial(serial.getTitle(), serial.getYear(),
                                            serial.getGenres(), serial.getNumberSeason(),
                                            serial.getCast(), serial.getSeasons());

            this.serials.add(newSerial);
        }
    }

    /**
     * Function for storing all the videos(movies and serials) together in my database
     * @param inputMovies The list of movies given on input
     * @param inputSerials The list of serials given on input
     */
    public void getAllVideos(final List<MovieInputData> inputMovies,
                             final List<SerialInputData> inputSerials) {
        this.videos.clear();
        for (MovieInputData movie : inputMovies) {
            Movie newMovie = new Movie(movie.getTitle(), movie.getYear(), movie.getGenres(),
                    movie.getDuration());

            this.videos.add(newMovie);
        }

        for (SerialInputData serial:inputSerials) {
            Serial newSerial = new Serial(serial.getTitle(), serial.getYear(),
                    serial.getGenres(), serial.getNumberSeason(),
                    serial.getCast(), serial.getSeasons());

            this.videos.add(newSerial);
        }
    }

    /**
     * Searches for a specific user in the Database by name
     * @param name The name of the user to be find
     * @return The found user(null if not)
     */
    public User findUserByName(final String name) {
        for (User user : this.users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Searches for a specific movie in the Database by name
     * @param name The name of the movie to be find
     * @return The found movie(null if not)
     */
    public Movie findMovieByName(final String name) {
        for (Movie movie : this.movies) {
            if (movie.getTitle().equals(name)) {
                return movie;
            }
        }

        return null;
    }

    /**
     * Searches for a specific serial in the Database by name
     * @param name The name of the serial to be find
     * @return The found serial(null if not)
     */
    public Serial findSerialByName(final String name) {
        for (Serial serial : this.serials) {
            if (serial.getTitle().equals(name)) {
                return serial;
            }
        }

        return null;
    }

    /**
     * Adds a new rating in the searched film's ratings list
     * @param name Name of the movie
     * @param rating Rating to be set
     */
    public void setMovieRating(final String name, final double rating) {
        for (Movie movie : this.movies) {
            if (movie.getTitle().equals(name)) {
                movie.getRatings().add(rating);
            }
        }
    }

    /**
     * Adds a new rating in a specific season's ratings list from a serial
     * @param name Name of the serial
     * @param rating Rating to be set
     * @param seasonNumber The season number to set rating for
     */
    public void setSeasonRating(final String name, final double rating, final int seasonNumber) {
        for (Serial serial : this.serials) {
            if (serial.getTitle().equals(name)) {
                Season season = serial.getSeasons().get(seasonNumber - 1);
                season.getRatings().add(rating);
            }
        }
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Serial> getSerials() {
        return serials;
    }

    public List<Video> getVideos() {
        return videos;
    }
}
