// This class defines all functions a door state class should have and a getter to check its own state

package baseNoStates.DoorState;

import baseNoStates.Door;

// Any door state class must implement this
// AKA state pattern
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
