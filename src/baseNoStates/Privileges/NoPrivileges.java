package baseNoStates.Privileges;

import baseNoStates.Space;
import java.time.LocalDateTime;

// A user assigned to this privilege level cannot perform any action ever, yet is a recognised user
public class NoPrivileges extends Privileges {
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
