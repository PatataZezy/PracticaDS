package baseNoStates;

import baseNoStates.requests.RequestReader;
import org.json.JSONObject;


public class Door {
    private final String id;
    private DoorState state;

    public Door(String id) {
        this.id = id;
        this.state = new DoorStateOpen();
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
        } else {
          System.out.println("Can't open door " + id + " because it's already open");
        }
        break;
      case Actions.CLOSE:
        if (closed) {
          System.out.println("Can't close door " + id + " because it's already closed");
        } else {
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
        this.state = this.state.update(action, this.id);
    }

    public boolean isClosed() {
        return this.state.isClosed();
    }

    public String getId() {
        return id;
    }

    public String getStateName() {
        return "unlocked";
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
}
