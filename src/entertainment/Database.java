package entertainment;

import actor.Actor;
import fileio.*;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<Actor> actors = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Movie> movies = new ArrayList<>();
    private final List<Serial> serials = new ArrayList<>();

    private static Database database = null;

    private Database() {}

    public static Database getDatabase() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

//    public void addActors(List<Actor> actors) {
//        this.actors.addAll(actors);
//    }
//
//    public void addUsers(List<User> users) {
//        this.users.addAll(users);
//    }
//
//    public void addMovies(List<Movie> movies) {
//        this.movies.addAll(movies);
//    }
//
//    public void addSerials(List<Serial> serials) {
//        this.serials.addAll(serials);
//    }

    public User findUserByName(String name) {
        for (User user : this.users) {
            if (user.getUsername().equals(name))
                return user;
        }
        return null;
    }

    public Movie findMovieByName(String name) {
        for (Movie movie : this.movies) {
            if (movie.getTitle().equals(name))
                return movie;
        }
        return null;
    }

    public void setMovieRating(String name, double rating) {
        for (Movie movie : this.movies) {
            if (movie.getTitle().equals(name)) {
                movie.getRatings().add(rating);
            }
        }
    }

    public void setSeasonRating(String name, double rating, int seasonNumber) {
        for (Serial serial : this.serials) {
            if (serial.getTitle().equals(name)) {
                Season season = serial.getSeasons().get(seasonNumber - 1);
                season.getRatings().add(rating);
            }
        }
    }

    public void transferActors(List<ActorInputData> inputActors) {
        for (ActorInputData actor:inputActors) {
            Actor newActor = new Actor(actor.getName(), actor.getCareerDescription(),
                                        actor.getFilmography(), actor.getAwards());
            this.actors.add(newActor);
        }
    }

    public void transferUsers(List<UserInputData> inputUsers) {
        for (UserInputData user:inputUsers) {
            User newUser = new User(user.getUsername(), user.getSubscriptionType(),
                                    user.getHistory(), user.getFavoriteMovies());
            this.users.add(newUser);
        }
    }

    public void transferMovies(List<MovieInputData> inputMovies) {
        for (MovieInputData movie:inputMovies) {
            Movie newMovie = new Movie(movie.getTitle(), movie.getYear(), movie.getGenres(), movie.getDuration());

            this.movies.add(newMovie);
        }
    }

    public void transferSerials(List<SerialInputData> inputSerials) {
        for (SerialInputData serial:inputSerials) {
            Serial newSerial = new Serial(serial.getTitle(), serial.getYear(),
                                            serial.getGenres(), serial.getNumberSeason(),
                                            serial.getCast(), serial.getSeasons());

            this.serials.add(newSerial);
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
}
