// this class assumes all interactions door can make while the actual state is closed, 
// it has all states variations from closed and a get state to check its own state

package baseNoStates.DoorState;

import baseNoStates.Door;

// State where door is closed but not locked
public class Closed extends DoorState {
  public Closed(Door door) {
    super(door);
  }

  @Override
  public void open() {
    this.door.setState(new Open(this.door));
  }

  @Override
  public void close() {
    System.out.println("Can't close door " + this.door.getId() + " because it's already closed");
  }

  @Override
  public void lock() {
    this.door.setState(new Locked(this.door));
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock door " + this.door.getId() + " because it's already unlocked");
  }

  @Override
  public void unlockShortly() {
    System.out.println("Can't unlock " + this.door.getId() + " shortly because it's not locked");
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
