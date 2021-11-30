package entertainment;

public class Rating extends Command {

    private double grade;
    private int seasonNumber;

    public Rating(final String type, final String username,
                  final String title, final double grade, final int seazonNumber) {
        super(type, username, title);
        this.grade = grade;
        this.seasonNumber = seazonNumber;
    }

    @Override
    public String applyCommand() {
        User user = Database.getDatabase().findUserByName(this.getUsername());

        if (user == null) {
            System.out.println("Invalid user!");
            return null;
        }
        if (!user.getHistory().containsKey(this.getTitle())) {
            return ("error -> " + this.getTitle() + " is not seen!");
        }

        if (user.getRatedMovies().contains(this.getTitle()) && this.seasonNumber == 0) {
            return ("error -> " + this.getTitle() + " has been already rated");
        }

        if (user.getRatedSerials().contains(this.getTitle() + seasonNumber) && this.seasonNumber != 0) {
            return ("error -> " + this.getTitle() + " has been already rated");
        }


        if (this.seasonNumber == 0) {
            Database.getDatabase().setMovieRating(this.getTitle(), this.grade);
            user.getRatedMovies().add(this.getTitle());
        } else {
            Database.getDatabase().setSeasonRating(this.getTitle(), this.grade, this.seasonNumber);
            user.getRatedSerials().add(this.getTitle() + this.seasonNumber);
        }

        return ("success -> " + this.getTitle() + " was rated with " + this.grade + " by " + user.getUsername());
    }
}
