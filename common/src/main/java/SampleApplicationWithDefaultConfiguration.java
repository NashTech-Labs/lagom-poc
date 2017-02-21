import com.netflix.config.ConcurrentCompositeConfiguration;
import com.netflix.config.ConcurrentMapConfiguration;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.samples.SampleApplication;

/**
 * A Sample Application built to showcase how to use the default ConcurrentCompositeConfiguration
 * registered with {@link DynamicPropertyFactory} and automatic registration with JMX
 * <p>
 * To run this sample application, add the following jars to your classpath:
 * <ul>
 * <li>archaius-core-xxx.jar (latest release/snapshot of archaius-core)
 * <li>commons-configuration-1.8.jar
 * <li>commons-lang-2.6.jar
 * <li>commons-logging-1.1.1.jar
 * </ul>
 *
 * @author awang
 *
 */
public class SampleApplicationWithDefaultConfiguration {
  static {
    // sampleapp.properties is packaged within the shipped jar file
    System.setProperty("archaius.configurationSource.defaultFileName", "sampleapp.properties");
    System.setProperty(DynamicPropertyFactory.ENABLE_JMX, "true");
    System.setProperty("this.is.test", "value from system property");
  }

  public static void main(String[] args) {
    new SampleApplication();
    ConcurrentCompositeConfiguration myConfiguration =
        (ConcurrentCompositeConfiguration) DynamicPropertyFactory.getInstance().getBackingConfigurationSource();


    ConcurrentMapConfiguration subConfig = new ConcurrentMapConfiguration();
    subConfig.setProperty("com.netflix.config.samples.SampleApp.SampleBean.name", "A Coffee Bean from Cuba");
    myConfiguration.setProperty("com.netflix.config.samples.sampleapp.prop1", "value1");

    myConfiguration.addConfiguration(subConfig);

    DynamicStringProperty prop = DynamicPropertyFactory.getInstance()
        .getStringProperty("this.is.test", "value from system property");

    prop.addCallback(() -> System.out.println("search url has changed to " + prop.get()));

    System.out.println("Started SampleApplication. Launch JConsole to inspect and update properties.");
    System.out.println("To see how callback work, update property com.netflix.config.samples.SampleApp.SampleBean.sensitiveBeanData from BaseConfigBean in JConsole");

  }
}