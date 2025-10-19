package baseNoStates.DoorState;

// State where door is propped (as in should be locked but is open)
public class DoorStatePropped implements DoorState {
    public DoorStatePropped() {
        // void
    }

    @Override
    public DoorState open(String id) {
        System.out.println("Can't open door " + id + " because it's already open");
        return this;
    }

    @Override
    public DoorState close(String id) {
        return new DoorStateLocked();
    }

    @Override
    public DoorState lock(String id) {
        System.out.println("Can't lock door " + id + " because it's propped");
        return this;
    }

    @Override
    public DoorState unlock(String id) {
        System.out.println("Can't unlock door " + id + " because it's propped");
        return this;
    }

    @Override
    public DoorState unlockShortly(String id) {
        System.out.println("Can't unlock door " + id + " shortly because it's propped");
        return this;
    }

    @Override
    public boolean isClosed() {
        return true;
    }

    @Override
    public String getState() {
        return "propped";
    }
}
