package baseNoStates.DoorState;

import baseNoStates.Door;

// Temporary state where door is unlocked for a short period of time
public class UnlockedShortly extends DoorState {
  boolean isOpen;

  public UnlockedShortly(Door door) {
    super(door);
    isOpen = false;
  }

  @Override
  public void open() {
    isOpen = true;
  }

  @Override
  public void close() {
    isOpen = false;
  }

  @Override
  public void lock() {
    System.out.println("Can't lock door" + this.door.getId()
            + " because it's waiting for a clock timeout");
  }

  @Override
  public void unlock() {
    System.out.println("Can't unlock door " + this.door.getId()
            + " shortly because it's already unlocked");
  }

  @Override
  public void unlockShortly() {
    System.out.println("Can't unlock door " + this.door.getId()
            + " shortly because it's already unlocked");
  }

  @Override
  public boolean isClosed() {
    return !this.isOpen;
  }

  @Override
  public String getState() {
    return "unlocked_shortly";
  }
}
