package baseNoStates;

import baseNoStates.DoorState.DoorState;
import baseNoStates.DoorState.Locked;
import baseNoStates.DoorState.Open;
import baseNoStates.DoorState.Propped;
import baseNoStates.requests.RequestReader;
import org.json.JSONObject;

// Handles all requests to a specific door
public class Door {
  private final String id;
  private baseNoStates.DoorState.DoorState state;

  private Space spaceComingFrom;
  private Space spaceLeadingTo;

  private Clock clock;

  // Kept for now, delete eventually
  public Door(String id) {
    this.id = id;
    this.state = new Open(this);
  }

  public Door(String id, Space spaceComingFrom, Space spaceLeadingTo) {
    this.id = id;
    this.state = new Open(this);

    this.spaceComingFrom = spaceComingFrom;
    this.spaceLeadingTo = spaceLeadingTo;

    spaceLeadingTo.addDoor(this);
  }

  // Takes in a request, executes only if it has been authorised beforehand
  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    }
    if (!request.isAuthorized()) {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  // Performs an already authorised action if possible
  // (ex. can close door if open, but cannot open it even if authorised because it's already open)
  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        this.state.open();
        break;

      case Actions.CLOSE:
        this.state.close();
        break;

      case Actions.LOCK:
        this.state.lock();
        break;

      case Actions.UNLOCK:
        this.state.unlock();
        break;

      case Actions.UNLOCK_SHORTLY:
        this.state.unlockShortly();
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

  // Only called if is in unlocked shortly state, starts timer to lock or set as propped
  protected void startTimer() {
    this.clock = new Clock(this, 10);
    this.clock.start();
  }

  // Called from clock when timer runs out (look Observer pattern)
  public void updateFromTimer() {
    if (this.state.getState().equals("unlocked_shortly")) {
      this.state = (this.state.isClosed() ? new Locked(this) : new Propped(this));
    }
  }

  public void setState(DoorState state) {
    this.state = state;
  }
}