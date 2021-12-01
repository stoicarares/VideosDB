package entertainment;

public class View extends Command {
    public View(final String type, final String username, final String title) {
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
            user.getHistory().put(this.getTitle(), 1);
        } else {
            user.getHistory().put(this.getTitle(), user.getHistory().get(this.getTitle()) + 1);
        }

        return ("success -> " + this.getTitle() + " was viewed with total views of " + user.getHistory().get(this.getTitle()));
    }
}
