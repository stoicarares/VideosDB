package actions.commands;

public abstract class Command {
    private final String type;
    private final String username;
    private final String title;

    public Command(final String type,  final String username, final String title) {
        this.type = type;
        this.username = username;
        this.title = title;
    }

    /**
     * Abstract method for apply the given command.
     * @return The string resulted due to the action
     */
    public abstract StringBuilder applyCommand();

    /**
     * Static method for getting the correct inititation for the given command.
     * @param type The command willing be done
     * @param username User's name who does the command
     * @param title The title of the movie
     * @param grade The grade users give at rating a video
     * @param seasonNumber The number of a serial's season that is going to be rated
     * @return The matched subclass's instance of the given command
     */
    public static Command instantiation(final String type, final String username,
                                        final String title, final double grade,
                                        final int seasonNumber) {

        return switch (type) {
            case "favorite" -> new Favorite(type, username, title);
            case "view" -> new View(type, username, title);
            case "rating" -> new Rating(type, username, title, grade, seasonNumber);
            default -> null;
        };
    }

    public final String getType() {
        return type;
    }

    public final String getUsername() {
        return username;
    }

    public final String getTitle() {
        return title;
    }
}
