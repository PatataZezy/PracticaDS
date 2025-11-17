package baseNoStates.DoorState;

import baseNoStates.Door;

// This class represents in what state a specific door may be in (open, closed, locked, etc.).
// Implements design pattern State with Door, as its state may change at any time.
public abstract class DoorState {
  protected Door door;

  public DoorState(Door door) {
    this.door = door;
  }

  public abstract void open();

  public abstract void close();

  public abstract void lock();

  public abstract void unlock();

  public abstract void unlockShortly();

  public abstract boolean isClosed();

  public abstract String getState();
}