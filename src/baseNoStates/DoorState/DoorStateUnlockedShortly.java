package baseNoStates.DoorState;

public class DoorStateUnlockedShortly implements DoorState {
    public DoorStateUnlockedShortly() {
        // void
    }

    @Override
    public DoorState open(String id) {
        return new DoorStatePropped();
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
        System.out.println("Can't unlock door " + id + " shortly because it's already unlocked");
        return this;
    }

    @Override
    public DoorState unlockShortly(String id) {
        System.out.println("Can't unlock door " + id + " shortly because it's already unlocked");
        return this;
    }

    public boolean isClosed() {
        return true;
    }
}
