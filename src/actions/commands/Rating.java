package actions.commands;

import entertainment.Database;
import entertainment.User;

public final class Rating extends Command {

    private final double grade;
    private final int seasonNumber;

    public Rating(final String type, final String username,
                  final String title, final double grade, final int seazonNumber) {
        super(type, username, title);
        this.grade = grade;
        this.seasonNumber = seazonNumber;
    }

    @Override
    public StringBuilder applyCommand() {
        User user = Database.getDatabase().findUserByName(this.getUsername());
        StringBuilder output = new StringBuilder();

        if (user == null) {
            System.out.println("Invalid user!");
            return null;
        }
        if (!user.getHistory().containsKey(this.getTitle())) {
            output.append("error -> ").append(this.getTitle()).append(" is not seen");

            return output;
        }

        if (user.getRatedMovies().contains(this.getTitle()) && this.seasonNumber == 0) {
            output.append("error -> ").append(this.getTitle()).append(" has been already rated");

            return output;
        }

        if (user.getRatedSerials().contains(this.getTitle() + seasonNumber)
                && this.seasonNumber != 0) {
            output.append("error -> ").append(this.getTitle()).append(" has been already rated");

            return output;
        }


        if (this.seasonNumber == 0) {
            Database.getDatabase().setMovieRating(this.getTitle(), this.grade);
            user.getRatedMovies().add(this.getTitle());
        } else {
            Database.getDatabase().setSeasonRating(this.getTitle(), this.grade, this.seasonNumber);
            user.getRatedSerials().add(this.getTitle() + this.seasonNumber);
        }

        output.append("success -> ").append(this.getTitle()).append(" was rated with ");
        output.append(this.grade).append(" by ").append(user.getUsername());

        return output;
    }

    public double getGrade() {
        return grade;
    }
}
