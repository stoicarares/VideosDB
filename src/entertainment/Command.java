package entertainment;

import fileio.Writer;
import net.sf.json.JSONArray;

public abstract class Command {
    private String type;
    private String username;
    private String title;

    public Command(final String type,  final String username, final String title) {
        this.type = type;
        this.username = username;
        this.title = title;
    }

    public abstract String applyCommand();

    public static Command instantiation(String type, String username,
                                       String title, double grade, int seasonNumber) {
        Command command = null;
        if (type.equals("favorite")) {
            command = new Favorite(type, username, title);
        } else if (type.equals("view")) {
            command = new View(type, username, title);
        } else if (type.equals("rating")) {
            command = new Rating(type, username, title, grade, seasonNumber);
        }

        return command;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }
}
