package baseNoStates.Privileges;

import baseNoStates.Space;

import java.time.LocalDateTime;

public class PrivilegesAdmin implements Privileges {
    public boolean canSendRequests(LocalDateTime now) {
        return true;
    }

    public boolean canBeInSpace(Space space) {
        return true;
    }

    public boolean canDoAction(String action) {
        return true;
    }
}
