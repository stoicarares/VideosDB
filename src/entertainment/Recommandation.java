package entertainment;

public class Recommandation {
    private String type;
    private String targetUser;

    public Recommandation(final String type, final String targetUser) {
        this.type = type;
        this.targetUser = targetUser;
    }

    public String getType() {
        return type;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }
}
