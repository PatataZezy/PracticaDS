package baseNoStates.DoorState;

import baseNoStates.Door;
import baseNoStates.Loggers;

// State where door is propped (as in should be locked but is open because it was unlocked shortly
// and never closed)
public class Propped extends DoorState {
  public Propped(Door door) {
    super(door);
  }

  @Override
  public void open() {
    Loggers.logger1.info("Can't open door " + this.door.getId() + " because it's already open");
  }

  @Override
  public void close() {
    this.door.setState(new Locked(this.door));
    Loggers.logger1.info("Door " + this.door.getId() + " opened");
  }

  @Override
  public void lock() {
    Loggers.logger1.info("Can't lock door " + this.door.getId() + " because it's propped");
  }

  @Override
  public void unlock() {
    Loggers.logger1.info("Can't unlock door " + this.door.getId() + " because it's propped");
  }

  @Override
  public void unlockShortly() {
    Loggers.logger1.info("Can't unlock door " + this.door.getId()
            + " shortly because it's propped");
  }

  @Override
  public boolean isClosed() {
    return false;
  }

  @Override
  public String getState() {
    return "propped";
  }
}