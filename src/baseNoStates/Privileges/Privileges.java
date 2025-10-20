package baseNoStates.Privileges;

import baseNoStates.Space;

import java.time.LocalDateTime;

/*
Defines all functions a privilege class should have
Any privilege class must implement this
AKA state pattern
*/
public interface Privileges {
    boolean canSendRequests(LocalDateTime now);
    boolean canBeInSpace(Space space);
    boolean canDoAction(String action);
}
