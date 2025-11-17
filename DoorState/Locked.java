package baseNoStates.DoorState;

import baseNoStates.Door;
import baseNoStates.Loggers;

import java.util.logging.Logger;

// State where door is closed and locked
public class Locked extends DoorState {
  public Locked(Door door) {
    super(door);
  }

  @Override
  public void open() {
    Loggers.logger1.info("Can't open door " + this.door.getId() + " because it's locked");
  }

  @Override
  public void close() {
    Loggers.logger1.info("Can't close door " + this.door.getId() + " because it's already closed");
  }

  @Override
  public void lock() {
    Loggers.logger1.info("Can't lock door " + this.door.getId() + " because it's already locked");
  }

  @Override
  public void unlock() {
    this.door.setState(new Closed(this.door));
    Loggers.logger1.info("Door " + this.door.getId() + " unlocked");
  }

  @Override
  public void unlockShortly() {
    this.door.setState(new UnlockedShortly(this.door));
    Loggers.logger1.info("Door " + this.door.getId() + " unlocked shortly");
  }

  @Override
  public boolean isClosed() {
    return true;
  }

  @Override
  public String getState() {
    return "locked";
  }
}
