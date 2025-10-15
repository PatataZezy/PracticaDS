package baseNoStates;

interface DoorState {
    DoorState update(String action, String id);
    boolean isClosed();
}
