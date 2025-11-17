package baseNoStates.DoorState;

import baseNoStates.Door;
import baseNoStates.Loggers;

// Temporary state where door is unlocked for a short period of time, and will leave this state once
// its timer runs out (see design pattern Observer between Door and Clock)
public class UnlockedShortly extends DoorState {
  boolean isOpen;

  public UnlockedShortly(Door door) {
    super(door);
    isOpen = false;
  }

  @Override
  public void open() {
    isOpen = true;
    Loggers.logger1.info("Door " + this.door.getId() + " opened");
  }

  @Override
  public void close() {
    isOpen = false;
    Loggers.logger1.info("Door " + this.door.getId() + " closed");
  }

  @Override
  public void lock() {
    Loggers.logger1.info("Can't lock door" + this.door.getId()
            + " because it's waiting for a clock timeout");
  }

  @Override
  public void unlock() {
    Loggers.logger1.info("Can't unlock door " + this.door.getId()
            + " shortly because it's already unlocked");
  }

  @Override
  public void unlockShortly() {
    Loggers.logger1.info("Can't unlock door " + this.door.getId()
            + " shortly because it's already unlocked");
  }

  @Override
  public boolean isClosed() {
    return !this.isOpen;
  }

  @Override
  public String getState() {
    return "unlocked_shortly";
  }
}
