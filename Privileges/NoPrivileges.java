package baseNoStates.Privileges;

import baseNoStates.Space;
import java.time.LocalDateTime;

// A user assigned this privilege level cannot perform any action ever, yet is recognised by the
// system as a valid user.
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
