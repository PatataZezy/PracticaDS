package baseNoStates;

public class DoorStateLocked implements DoorState {
    public DoorStateLocked() {
        // void
    }

    @Override
    public DoorState update(String action, String id) {
        switch (action) {
            case Actions.OPEN:
                System.out.println("Can't open door " + id + " because it's locked");
                break;

            case Actions.CLOSE:
                System.out.println("Can't close door " + id + " because it's already closed");
                break;

            case Actions.LOCK:
                System.out.println("Can't lock door " + id + " because it's already locked");
                break;

            case Actions.UNLOCK:
                return new DoorStateClosed();

            case Actions.UNLOCK_SHORTLY:
                return new DoorStateUnlockedShortly();

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
