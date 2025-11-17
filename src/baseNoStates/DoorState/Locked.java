// this class assumes all interactions door can make while the actual state is locked, 
// it has all states variations from locked and a get state to check its own state

package baseNoStates.DoorState;

import baseNoStates.Door;

// State where door is closed and locked
public class Locked extends DoorState {
  public Locked(Door door) {
    super(door);
  }

  @Override
  public void open() {
    System.out.println("Can't open door " + this.door.getId() + " because it's locked");
  }

  @Override
  public void close() {
    System.out.println("Can't close door " + this.door.getId() + " because it's already closed");
  }

  @Override
  public void lock() {
    System.out.println("Can't lock door " + this.door.getId() + " because it's already locked");
  }

  @Override
  public void unlock() {
    this.door.setState(new Closed(this.door));
  }

  @Override
  public void unlockShortly() {
    this.door.setState(new UnlockedShortly(this.door));
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
