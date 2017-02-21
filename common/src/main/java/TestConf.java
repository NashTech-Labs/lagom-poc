import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.netflix.config.AbstractPollingScheduler;
import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.PollResult;
import com.netflix.config.PolledConfigurationSource;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MetricPoller;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

public class TestConf {
  public static void main(String[] args) throws ConfigurationException {

    // enable JMX dynamic configuration
    System.setProperty(DynamicPropertyFactory.ENABLE_JMX, "true");

    // enable config from properties file
    ConcurrentMapConfiguration configFile =
        new ConcurrentMapConfiguration(new PropertiesConfiguration("config.properties"));
    // enable configuration from system properties
    ConcurrentMapConfiguration systemProps =
        new ConcurrentMapConfiguration(new SystemConfiguration());

    // composite configuration
    ConcurrentCompositeConfiguration config = new ConcurrentCompositeConfiguration();
    config.addConfiguration(configFile);
    config.addConfiguration(systemProps);
    ConfigurationManager.install(config);


    ConcurrentCompositeConfiguration myConfiguration =
        (ConcurrentCompositeConfiguration) DynamicPropertyFactory.getInstance().getBackingConfigurationSource();



    DynamicStringProperty prop = DynamicPropertyFactory.getInstance()
        .getStringProperty("search.url", "https://www.google.com/#q=");

    System.out.println("search url is: " + prop.get());
    while(true) {
      prop.addCallback(() -> System.out.println("search url has changed to " + prop.get()));
    }























    /*DynamicConfiguration configuration =
        new DynamicConfiguration(new PolledConfigurationSource() {
          @Override public PollResult poll(boolean initial, Object checkPoint) throws Exception {
            return null;
          }
        }, new AbstractPollingScheduler() {
          @Override protected void schedule(Runnable pollingRunnable) {

          }

          @Override public void stop() {

          }
        });

    DynamicStringProperty timeToWait =
        DynamicPropertyFactory.getInstance().getStringProperty("this.is.test", "testtttt");
    final List<MetricObserver> observers = new ArrayList<MetricObserver>();

    *//*final MetricPoller poller = new MonitorRegistryMetricPoller();
    final PollRunnable task = new PollRunnable(poller, BasicMetricFilter.MATCH_ALL, observers);
    PollScheduler.getInstance().start();
    PollScheduler.getInstance()
        .addPoller(task, DynamicPropertyFactory.getInstance().getLongProperty("metrics.poll.interval", 1).getValue(), TimeUnit.SECONDS);*//*

    while (true) {
      System.out.println("prop is :" + timeToWait.get());
    }*/
  }
}
