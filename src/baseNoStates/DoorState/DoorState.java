package baseNoStates.DoorState;

/*
Defines all functions a door state class should have
Any door state class must implement this
*/
public interface DoorState {
    DoorState open(String id);
    DoorState close(String id);
    DoorState lock(String id);
    DoorState unlock(String id);
    DoorState unlockShortly(String id);

    boolean isClosed();
    String getState();
}