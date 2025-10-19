package baseNoStates.DoorState;

// State where door is simply open
public class DoorStateOpen implements DoorState {
    public DoorStateOpen() {
        // void
    }

    @Override
    public DoorState open(String id) {
        System.out.println("Can't open door " + id + " because it's already open");
        return this;
    }

    @Override
    public DoorState close(String id) {
        return new DoorStateClosed();
    }

    @Override
    public DoorState lock(String id) {
        System.out.println("Can't lock door " + id + " because it's open");
        return this;
    }

    @Override
    public DoorState unlock(String id) {
        System.out.println("Can't unlock " + id + " shortly because it's not closed");
        return this;
    }

    @Override
    public DoorState unlockShortly(String id) {
        System.out.println("Can't unlock " + id + " shortly because it's not closed");
        return this;
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
