package baseNoStates;

public class DoorStateUnlockedShortly implements DoorState {
    public DoorStateUnlockedShortly() {
        // void
    }

    @Override
    public DoorState update(String action, String id) {
        switch (action) {
            case Actions.OPEN:
                return new DoorStatePropped();

            case Actions.CLOSE:
                return new DoorStateLocked();

            case Actions.LOCK:
                return new DoorStateLocked();

            case Actions.UNLOCK:
                System.out.println("Can't unlock door " + id + " because it's already unlocked");
                break;

            case Actions.UNLOCK_SHORTLY:
                System.out.println("Can't unlock door " + id + " shortly because it's already unlocked");
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
