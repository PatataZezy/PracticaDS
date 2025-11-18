package baseNoStates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

// Observable class from design pattern Observer for unlocked shortly doors, notifies observers at a
// constant rate in case they may need to know the current time.
public final class Clock {
  private static final Timer timer = new Timer();
  private static ArrayList<Door> observers;

  public static void start() {
    // Creates a new timer thread that notifies all Door observers at a constant rate (ex. 0.1
    // seconds)
    TimerTask repeatedTask = new TimerTask() {
      public void run() { // instance of anonymous class
        LocalDateTime now = LocalDateTime.now();
        for (Door door : observers) {
          door.updateFromTimer(now);
        }
      }
    };
    int refreshRate = 100; // Milliseconds
    timer.scheduleAtFixedRate(repeatedTask, 0L, refreshRate);
  }

  public static void addObservers(ArrayList<Door> doors) {
    observers = doors;
  }
}
