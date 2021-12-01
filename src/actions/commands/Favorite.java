package actions.commands;

import entertainment.Database;
import entertainment.User;

public final class Favorite extends Command {

    public Favorite(final String type, final String username, final String title) {
        super(type, username, title);
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

        } else {
            if (user.getFavoriteMovies().contains(this.getTitle())) {
                output.append("error -> ").append(this.getTitle());
                output.append(" is already in favourite list");
            } else {
                user.getFavoriteMovies().add(this.getTitle());
                output.append("success -> ").append(this.getTitle());
                output.append(" was added as favourite");
            }

        }

        return output;
    }


}
