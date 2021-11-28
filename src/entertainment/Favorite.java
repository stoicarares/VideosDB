package entertainment;

import fileio.Writer;
import net.sf.json.JSONArray;

public class Favorite extends Command {

    public Favorite(final String type, final String username, final String title) {
        super(type, username, title);
    }

    @Override
    public String applyCommand() {
        User user = Database.getDatabase().findUserByName(this.getUsername());

        if (user == null) {
            System.out.println("Invalid user!");
            return null;
        }

        if (!user.getHistory().containsKey(this.getTitle())) {
            return ("error -> " + this.getTitle() + " is not seen");

        } else {
            if (user.getFavoriteMovies().contains(this.getTitle())) {
                return ("error -> " + this.getTitle() + " is already in favourite list");

            } else {
                user.getFavoriteMovies().add(this.getTitle());
                return ("success -> " + this.getTitle() + " was added as favourite");
            }
        }
    }


}
