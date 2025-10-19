package baseNoStates;

import java.time.LocalDateTime;

public class User {
    private final String name;
    private final String credential;
    private UserGroup userGroup;

    public User(String name, String credential) {
        this.name = name;
        this.credential = credential;
    }

    public String getCredential() {
        return credential;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public boolean canSendRequests(LocalDateTime now) {
        return this.userGroup.canSendRequests(now);
    }

    public boolean canBeInSpace(Space space) {
        return this.userGroup.canBeInSpace(space);
    }

    public boolean canDoAction(String action) {
        return this.userGroup.canDoAction(action);
    }

    @Override
    public String toString() {
        return "User{name=" + name + ", credential=" + credential + "}";
    }
}
