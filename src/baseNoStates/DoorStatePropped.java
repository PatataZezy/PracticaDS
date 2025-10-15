package baseNoStates;

public class DoorStatePropped implements DoorState {
    public DoorStatePropped() {
        // void
    }

    @Override
    public DoorState update(String action, String id) {
        switch (action) {
            case Actions.OPEN:
                System.out.println("Can't open door " + id + " because it's already open");
                break;

            case Actions.CLOSE:
                return new DoorStateLocked();

            case Actions.LOCK:
                System.out.println("Can't lock door " + id + " because it's propped");
                break;

            case Actions.UNLOCK:
                System.out.println("Can't unlock door " + id + " because it's propped");
                break;

            case Actions.UNLOCK_SHORTLY:
                System.out.println("Can't unlock door " + id + " shortly because it's propped");
                break;

            default:
                assert false : "Unknown action " + action;
                System.exit(-1);
        }

        return this;
    }

    public boolean isClosed() {
        return true;
    }
}
