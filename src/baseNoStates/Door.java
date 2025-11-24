package baseNoStates;

import baseNoStates.DoorState.DoorState;
import baseNoStates.DoorState.Locked;
import baseNoStates.DoorState.Open;
import baseNoStates.DoorState.Propped;
import baseNoStates.requests.RequestReader;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.json.JSONObject;

// This class represents any one door in the building, and will process any action regarding it. It
// is also used as observer class with class Clock for handling temporary unlocked doors (See
// method updateFromTimer()).
public class Door {
  private final String id;
  private baseNoStates.DoorState.DoorState state;

  private final Space spaceComingFrom;
  private final Space spaceLeadingTo;

  private LocalDateTime timerStart;

  public Door(String id, Space spaceComingFrom, Space spaceLeadingTo) {
    this.id = id;
    this.state = new Open(this);

    this.spaceComingFrom = spaceComingFrom;
    this.spaceLeadingTo = spaceLeadingTo;

    this.timerStart = LocalDateTime.now();

    spaceLeadingTo.addDoor(this);
  }

  // Takes in a request, executes only if it has been authorised beforehand
  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    Loggers.logger1.debug("Processing request " + request.getCredentialTimeAction() + " at Door "
            + this.id + "...");
    if (request.isAuthorized()) {
      Loggers.logger1.debug("Request at door " + this.id
          + " " + request.getCredentialTimeAction()
          + " authorised, proceeding to performing action...");
      doAction(request.getAction());
      return;
    } else {
      Loggers.logger1.info("Request at door " + this.id + " " + request.getCredentialTimeAction()
              + " unauthorised");
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
        // Needed in case the door was not locked (In that case it will not be unlocked shortly)
        if (this.state.getState().equals("unlocked_shortly")) {
          this.timerStart = LocalDateTime.now();
        }
        break;

      default:
        Loggers.logger1.error("Unknown action detected");
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public boolean isClosed() {
    return this.state.isClosed();
  }

  public boolean isPropped() {
    return this.state.getState().equals("propped");
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

  private boolean unlockTimerRanOut(LocalDateTime now) {
    return (this.state.getState().equals("unlocked_shortly"))
        && (ChronoUnit.SECONDS.between(this.timerStart, now) >= 10);
  }

  // Called from clock when timer runs out (Observer pattern)
  public void updateFromTimer(LocalDateTime now) {
    if (this.unlockTimerRanOut(now)) {
      this.state = (this.state.isClosed() ? new Locked(this) : new Propped(this));
      Loggers.logger1.info("Timer at door " + this.id + " ended, changing state to "
              + this.state.getState());
    }
  }

  public void setState(DoorState state) {
    this.state = state;
  }
}