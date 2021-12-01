package actions.commands;

import entertainment.Database;
import entertainment.User;

public final class View extends Command {
    public View(final String type, final String username, final String title) {
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
            user.getHistory().put(this.getTitle(), 1);
        } else {
            user.getHistory().put(this.getTitle(), user.getHistory().get(this.getTitle()) + 1);
        }

        output.append("success -> ").append(this.getTitle());
        output.append(" was viewed with total views of ");
        output.append(user.getHistory().get(this.getTitle()));

        return output;
    }
}
