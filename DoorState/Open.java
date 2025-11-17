package baseNoStates.DoorState;

import baseNoStates.Door;
import baseNoStates.Loggers;

// State where door is simply open
public class Open extends DoorState {
  public Open(Door door) {
    super(door);
  }

  @Override
  public void open() {
    Loggers.logger1.info("Can't open door " + this.door.getId() + " because it's already open");
  }

  @Override
  public void close() {
    this.door.setState(new Closed(this.door));
    Loggers.logger1.info("Door " + this.door.getId() + " closed");
  }

  @Override
  public void lock() {
    Loggers.logger1.info("Can't lock door " + this.door.getId() + " because it's open");
  }

  @Override
  public void unlock() {
    Loggers.logger1.info("Can't unlock " + this.door.getId() + " because it's not closed");
  }

  @Override
  public void unlockShortly() {
    Loggers.logger1.info("Can't unlock " + this.door.getId() + " shortly because it's not closed");
  }

  @Override
  public boolean isClosed() {
    return false;
  }

  @Override
  public String getState() {
    return "unlocked";
  }
}
