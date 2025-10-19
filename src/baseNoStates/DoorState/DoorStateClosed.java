package baseNoStates.DoorState;

// State where door is closed but not locked
public class DoorStateClosed implements DoorState {
    public DoorStateClosed() {
        // void
    }

    @Override
    public DoorState open(String id) {
        return new DoorStateOpen();
    }

    @Override
    public DoorState close(String id) {
        System.out.println("Can't close door " + id + " because it's already closed");
        return this;
    }

    @Override
    public DoorState lock(String id) {
        return new DoorStateLocked();
    }

    @Override
    public DoorState unlock(String id) {
        System.out.println("Can't unlock door " + id + " because it's already unlocked");
        return this;
    }

    @Override
    public DoorState unlockShortly(String id) {
        System.out.println("Can't unlock " + id + " shortly because it's not locked");
        return this;
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
