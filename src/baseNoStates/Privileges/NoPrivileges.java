package baseNoStates.Privileges;

import baseNoStates.Space;

import java.time.LocalDateTime;

public class NoPrivileges implements Privileges {
    public boolean canSendRequests(LocalDateTime now) {
        return false;
    }

    public boolean canBeInSpace(Space space) {
        return false;
    }

    public boolean canDoAction(String action) {
        return false;
    }
}
