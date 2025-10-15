package baseNoStates.DoorState;

public interface DoorState {
    DoorState open(String id);
    DoorState close(String id);
    DoorState lock(String id);
    DoorState unlock(String id);
    DoorState unlockShortly(String id);

    boolean isClosed();
}
