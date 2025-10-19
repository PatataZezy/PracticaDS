package baseNoStates;

import baseNoStates.requests.RequestReader;
import baseNoStates.DoorState.*;
import org.json.JSONObject;

public class Door {
    private final String id;
    private baseNoStates.DoorState.DoorState state;

    private Space spaceComingFrom;
    private Space spaceLeadingTo;

    private Clock clock;

    // Kept for now, delete eventually
    public Door(String id) {
        this.id = id;
        this.state = new DoorStateOpen();
    }

    public Door(String id, Space spaceComingFrom, Space spaceLeadingTo) {
        this.id = id;
        this.state = new DoorStateOpen();

        this.spaceComingFrom = spaceComingFrom;
        this.spaceLeadingTo = spaceLeadingTo;

        spaceLeadingTo.addDoor(this);
    }

    public void processRequest(RequestReader request) {
        // it is the Door that process the request because the door has and knows
        // its state, and if closed or open
        if (request.isAuthorized()) {
            String action = request.getAction();
            doAction(action);
        }
        else {
            System.out.println("not authorized");
        }
        request.setDoorStateName(getStateName());
    }

    private void doAction(String action) {
        /*
        // Si esta en estat propped, temporitzador 10 segs
        switch (action) {
            case Actions.OPEN:
                if (closed) {
                    closed = false;
                }
                else {
                    System.out.println("Can't open door " + id + " because it's already open");
                }
                break;

            case Actions.CLOSE:
                if (closed) {
                    System.out.println("Can't close door " + id + " because it's already closed");
                }
                else {
                    closed = true;
                }
                break;

            case Actions.LOCK:
                // TODO
                if (closed && unlocked) {
                    locked = true;
                }
                else if (open) {
                    System.out.println("Can't lock door " + id + " because it's open");
                }
                else if (closed && locked){
                    System.out.println("Can't lock door " + id + " because it's already locked");
                }
                break;

            // fall through
            case Actions.UNLOCK:
                // TODO
                if (closed && unlocked) {
                    System.out.println("Can't unlock door " + id + " because it's already unlocked");
                }
                else if (open) {
                    System.out.println("Can't unlock door " + id + " because it's open");
                }
                else if (closed && locked){
                    unlocked = true;
                }
                break;

                // fall through
            case Actions.UNLOCK_SHORTLY:
                // TODO
                System.out.println("Action " + action + " not implemented yet");
                break;

            default:
                assert false : "Unknown action " + action;
                System.exit(-1);
        }
        */
        switch (action) {
            case Actions.OPEN:
                this.state = this.state.open(this.id);
                break;

            case Actions.CLOSE:
                this.state = this.state.close(id);
                break;

            case Actions.LOCK:
                this.state = this.state.lock(id);
                break;

            case Actions.UNLOCK:
                this.state = this.state.unlock(id);
                break;
            // fall through
            case Actions.UNLOCK_SHORTLY:
                /*
                // TODO
                System.out.println("Action " + action + " not implemented yet");
                break;
                */
                this.state = this.state.unlockShortly(id);
                if (this.state.getState().equals("unlocked_shortly")) {
                    this.startTimer();
                }
                break;

            default:
                assert false : "Unknown action " + action;
                System.exit(-1);
        }
    }

    public boolean isClosed() {
        return this.state.isClosed();
    }

    public String getId() {
        return id;
    }

    public String getStateName() {
        return this.state.getState();
    }

    @Override
    public String toString() {
        return "Door{"
            + ", id='" + id + '\''
            + ", closed=" + this.isClosed()
            + ", state=" + getStateName()
            + "}";
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("state", getStateName());
        json.put("closed", this.isClosed());
        return json;
    }

    public Space getSpaceComingFrom() {
        return this.spaceComingFrom;
    }

    public Space getSpaceLeadingTo() {
        return this.spaceLeadingTo;
    }

    protected void startTimer() {
        this.clock = new Clock(this, 10);
        this.clock.start();
    }

    public void updateFromTimer() {
        if (this.state.getState().equals("unlocked_shortly")) {
            this.state = (this.state.isClosed() ? new DoorStateLocked() : new DoorStatePropped());
        }
    }
}
