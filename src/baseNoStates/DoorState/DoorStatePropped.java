package baseNoStates.DoorState;

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

    public boolean isClosed() {
        return true;
    }
}
