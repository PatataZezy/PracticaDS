package baseNoStates.Privileges;

import baseNoStates.Space;
import java.time.LocalDateTime;
import java.time.Month;

// This class represents the set of privileges any one user may have, and can decide whether it is
// authorised to perform a certain action at a certain time or not. Implements design pattern State.
public abstract class Privileges {
  public abstract boolean canSendRequests(LocalDateTime now);

  public abstract boolean canBeInSpace(Space space);

  public abstract boolean canDoAction(String action);

  // Checks if date is within 2025/09/01 and 2026/02/28
  protected boolean isWithinAllowedPeriod(LocalDateTime now) {
    if (now.getYear() == 2025
          && (now.getMonth() == Month.SEPTEMBER
          || now.getMonth() == Month.OCTOBER
          || now.getMonth() == Month.NOVEMBER
          || now.getMonth() == Month.DECEMBER)) {

      return true;
    }

    return (now.getYear() == 2026
          && (now.getMonth() == Month.JANUARY
          || now.getMonth() == Month.FEBRUARY));
  }
}
