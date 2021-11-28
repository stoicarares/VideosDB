package entertainment;

public class Rating extends Command {

    private double grade;
    private int seazonNumber;

    public Rating(final String type, final String username,
                  final String title, final double grade, final int seazonNumber) {
        super(type, username, title);
        this.grade = grade;
        this.seazonNumber = seazonNumber;
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

        if (user.getRatedMovies().contains(this.getTitle())) {
            return ("error -> " + this.getTitle() + "has been already rated");
        }

        user.getRatedMovies().add(this.getTitle());

        if (this.seazonNumber == 0) {
            Database.getDatabase().setMovieRating(this.getTitle(), this.grade);
        } else {
            Database.getDatabase().setSeasonRating(this.getTitle(), this.grade, this.seazonNumber);
        }

        return ("success -> " + this.getTitle() + " was rated with " + this.grade + " by " + user.getUsername());
    }
}
