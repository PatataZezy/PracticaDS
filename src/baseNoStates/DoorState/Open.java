// this class assumes all interactions door can make while the actual state is open, 
// it has all states variations from open and a get state to check its own state

package baseNoStates.DoorState;

import baseNoStates.Door;

// State where door is simply open
public class Open extends DoorState {
  public Open(Door door) {
    super(door);
  }

  @Override
  public void open() {
    System.out.println("Can't open door " + this.door.getId() + " because it's already open");
  }

  @Override
  public void close() {
    this.door.setState(new Closed(this.door));
  }

  @Override
  public void lock() {
    System.out.println("Can't lock door " + this.door.getId() + " because it's open");
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock " + this.door.getId() + " shortly because it's not closed");
  }

  @Override
  public void unlockShortly() {
    System.out.println("Can't unlock " + this.door.getId() + " shortly because it's not closed");
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
