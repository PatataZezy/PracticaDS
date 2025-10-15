package baseNoStates;

public class DoorStateOpen implements DoorState {
    public DoorStateOpen() {
        // void
    }

    @Override
    public DoorState update(String action, String id) {
        switch (action) {
            case Actions.OPEN:
                System.out.println("Can't open door " + id + " because it's already open");
                break;

            case Actions.CLOSE:
                return new DoorStateClosed();

            case Actions.LOCK:
                System.out.println("Can't lock door " + id + " because it's open");
                break;

            case Actions.UNLOCK:
                System.out.println("Can't unlock door " + id + " because it's open");
                break;

            case Actions.UNLOCK_SHORTLY:
                System.out.println("Can't unlock " + action + " shortly because it's not closed");
                break;

            default:
                assert false : "Unknown action " + action;
                System.exit(-1);
        }

        return this;
    }

    public boolean isClosed() {
        return false;
    }
}
