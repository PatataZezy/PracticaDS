package baseNoStates;

public class DoorStateClosed implements DoorState {
    public DoorStateClosed() {
        // void
    }

    @Override
    public DoorState update(String action, String id) {
        switch (action) {
            case Actions.OPEN:
                return new DoorStateOpen();

            case Actions.CLOSE:
                System.out.println("Can't close door " + id + " because it's already closed");
                break;

            case Actions.LOCK:
                return new DoorStateLocked();

            case Actions.UNLOCK:
                System.out.println("Can't unlock door " + id + " because it's already unlocked");
                break;

            case Actions.UNLOCK_SHORTLY:
                System.out.println("Can't unlock " + action + " shortly because it's not locked");
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
