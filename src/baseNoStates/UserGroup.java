package baseNoStates;

import baseNoStates.Privileges.Privileges;

import java.time.LocalDateTime;

public class UserGroup {
    private User[] users;
    private Privileges privileges;

    public UserGroup(User[] users, Privileges privileges) {
        this.users = users;
        this.privileges = privileges;
    }

    public User findUserByCredential(String credential) {
        for (User user : users) {
            if (user.getCredential().equals(credential)) {
                return user;
            }
        }

        return null;
    }

    public boolean canSendRequests(LocalDateTime now) {
        return privileges.canSendRequests(now);
    }

    public boolean canBeInSpace(Space space) {
        return privileges.canBeInSpace(space);
    }

    public boolean canDoAction(String action) {
        return privileges.canDoAction(action);
    }
}
