import com.netflix.config.AbstractPollingScheduler;


public class MyScheduler extends AbstractPollingScheduler {

  @Override
  protected synchronized void schedule(Runnable runnable) {
    // schedule the runnable
  }

  @Override
  public void stop() {
    // stop the scheduler
  }
}
