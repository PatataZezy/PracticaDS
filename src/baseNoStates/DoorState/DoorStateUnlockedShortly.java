package baseNoStates.DoorState;

import baseNoStates.Clock;

import java.util.Observable;
import java.util.Observer;

// Temporary state where door is closed but not locked for a short period of time
public class DoorStateUnlockedShortly implements DoorState{
    boolean isOpen;

    public DoorStateUnlockedShortly() {
        isOpen = false;
    }

    @Override
    public DoorState open(String id) {
        isOpen = true;
        return this;
    }

    @Override
    public DoorState close(String id) {
        isOpen = false;
        return this;
    }

    @Override
    public DoorState lock(String id) {
        System.out.println("Can't lock door" + id + " because it's waiting for a clock timeout");
        return this;
    }

    @Override
    public DoorState unlock(String id) {
        System.out.println("Can't unlock door " + id + " shortly because it's already unlocked");
        return this;
    }

    @Override
    public DoorState unlockShortly(String id) {
        System.out.println("Can't unlock door " + id + " shortly because it's already unlocked");
        return this;
    }

    @Override
    public boolean isClosed() {
        return !this.isOpen;
    }

    @Override
    public String getState() {
        return "unlocked_shortly";
    }
}
