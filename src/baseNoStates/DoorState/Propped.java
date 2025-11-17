// this class assumes all interactions door can make while the actual state is propped, 
// it has all states variations from propped and a get state to check its own state

package baseNoStates.DoorState;

import baseNoStates.Door;

// State where door is propped (as in should be locked but is open)
public class Propped extends DoorState {
  public Propped(Door door) {
    super(door);
  }

  @Override
  public void open() {
    System.out.println("Can't open door " + this.door.getId() + " because it's already open");
  }

  @Override
  public void close() {
    this.door.setState(new Locked(this.door));
  }

  @Override
  public void lock() {
    System.out.println("Can't lock door " + this.door.getId() + " because it's propped");
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock door " + this.door.getId() + " because it's propped");
  }

  @Override
  public void unlockShortly() {
    System.out.println("Can't unlock door " + this.door.getId() + " shortly because it's propped");
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
