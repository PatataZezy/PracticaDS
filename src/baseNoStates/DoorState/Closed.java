package baseNoStates.DoorState;

import baseNoStates.Door;
import baseNoStates.Loggers;

// State where door is closed but not locked
public class Closed extends DoorState {
  public Closed(Door door) {
    super(door);
  }

  @Override
  public void open() {
    this.door.setState(new Open(this.door));
    Loggers.logger1.info("Door " + this.door.getId() + " opened");
  }

  @Override
  public void close() {
    Loggers.logger1.info("Can't close door " + this.door.getId() + " because it's already closed");
  }

  @Override
  public void lock() {
    this.door.setState(new Locked(this.door));
    Loggers.logger1.info("Door " + this.door.getId() + " locked");
  }

  @Override
  public void unlock() {
    Loggers.logger1.info("Can't unlock door " + this.door.getId()
            + " because it's already unlocked");
  }

  @Override
  public void unlockShortly() {
    Loggers.logger1.info("Can't unlock " + this.door.getId() + " shortly because it's not locked");
  }

  @Override
  public boolean isClosed() {
    return true;
  }

  @Override
  public String getState() {
    return "unlocked";
  }
}
