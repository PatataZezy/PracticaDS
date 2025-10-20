package baseNoStates.DoorState;
// Renombrar a solo Locked
// State where door is closed and locked
public class DoorStateLocked implements DoorState {
    public DoorStateLocked() {
        // void
    }

    @Override
    public DoorState open(String id) {
        System.out.println("Can't open door " + id + " because it's locked");
        return this;
    }

    @Override
    public DoorState close(String id) {
        System.out.println("Can't close door " + id + " because it's already closed");
        return this;
    }

    @Override
    public DoorState lock(String id) {
        System.out.println("Can't lock door " + id + " because it's already locked");
        return this;
    }

    @Override
    public DoorState unlock(String id) {
        return new DoorStateClosed();
    }

    @Override
    public DoorState unlockShortly(String id) {
        return new DoorStateUnlockedShortly();
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
