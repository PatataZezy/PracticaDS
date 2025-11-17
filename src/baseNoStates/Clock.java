package baseNoStates;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

// Observable class from design pattern Observer for unlocked shortly doors, starts timer when state
// is enabled and updates door's state accordingly when timer runs out.
public class Clock {
  private LocalDateTime date;
  private Timer timer;
  private int period; // seconds
  private Door observer;

  public Clock(Door observer, int period) {
    this.observer = observer;
    this.period = period;
    timer = new Timer();
  }

  public void start() {
    // Creates a new timer thread that counts up to 10 seconds and notifies its Door observer once
    // it's finished.
    TimerTask repeatedTask = new TimerTask() {
      public void run() { // instance of anonymous class
        observer.updateFromTimer();
      }
    };
    timer.schedule(repeatedTask, 1000L * period);
  }

  public void stop() {
    timer.cancel();
  }

  public int getPeriod() {
    return period;
  }

  public LocalDateTime getDate() {
    return date;
  }
}
