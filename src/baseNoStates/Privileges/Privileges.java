package baseNoStates.Privileges;

import baseNoStates.Space;

import java.time.LocalDateTime;

public interface Privileges {
    boolean canSendRequests(LocalDateTime now);
    boolean canBeInSpace(Space space);
    boolean canDoAction(String action);
}
