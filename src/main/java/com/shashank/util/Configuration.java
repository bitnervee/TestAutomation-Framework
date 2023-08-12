package com.shashank.util;

import com.shashank.driver.DriverType;
import com.shashank.framework.LogMe;
import java.io.File;
import java.util.Iterator;
import java.util.logging.Logger;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Configuration {

  public static final String ENV = "config.env";
  public static final String CONFIG = "config.config";
  private static final LogMe logs = new LogMe(Configuration.class.getName());
  private static final Configuration instance = new Configuration();
  private final String fileLocation = FileUtils.getFileLocation("config.properties");
  private CompositeConfiguration prop;

  protected Configuration() {
    init();
  }

  public static Configuration getInstance() {
    return instance;
  }

  private void init() {
    prop = new CompositeConfiguration();
    prop.addConfiguration(new SystemConfiguration());
    try {
      prop.addConfiguration(build());
    } catch (ConfigurationException e) {
      throw new IllegalStateException(e);
    }
    printConfigDetails();
  }

  private void printConfigDetails() {
    logs.info("*********************************************************");
    logs.info("CONFIGURATION DETAILS");
    logs.info("*********************************************************");
    for (Iterator<String> iterator = prop.getKeys(); iterator.hasNext(); ) {
      String key = iterator.next();
      logs.info(key + "-----------" + getPropertiesAsString(key));
    }
    logs.info("*********************************************************");
  }

  private FileBasedConfiguration build() throws ConfigurationException {
    String env = System.getProperty(ENV);
    File propertiesFile = null;

    if (env != null) {
      String file = FileUtils.getFileLocation(env + ".properties");
      propertiesFile = new File(file);
      if (!propertiesFile.exists() || !propertiesFile.isFile()) {
        throw new IllegalArgumentException(
            env + ".config file is not available in src/main/resources folder");
      }
    } else {
      String file = System.getProperty(CONFIG, fileLocation);
      propertiesFile = new File(file);
    }

    Logger.getGlobal()
        .warning("Working With Configuration File [" + propertiesFile.getAbsolutePath() + "]");
    Parameters params = new Parameters();
    FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
        PropertiesConfiguration.class)
        .configure(params.fileBased().setFile(propertiesFile));

    return builder.getConfiguration();
  }

  protected String getPropertiesAsString(String key) {
    if (TestNGConfigParams.getTestParams().containsKey(key)) {
      return TestNGConfigParams.getTestParams().get(key);
    }
    return prop.getString(key);
  }

  protected String getPropertiesAsString(String key, String defaultVal) {
    if (TestNGConfigParams.getTestParams().containsKey(key)) {
      return TestNGConfigParams.getTestParams().get(key);
    }
    return prop.getString(key, defaultVal);
  }

  protected int getPropertiesAsInt(String key) {
    if (TestNGConfigParams.getTestParams().containsKey(key)) {
      return Integer.parseInt(TestNGConfigParams.getTestParams().get(key));
    }
    return prop.getInt(key);
  }

  protected int getPropertiesAsInt(String key, int defaultVal) {
    if (TestNGConfigParams.getTestParams().containsKey(key)) {
      return Integer.parseInt(TestNGConfigParams.getTestParams().get(key));
    }
    return prop.getInt(key, defaultVal);
  }

  protected boolean getPropertiesAsBoolean(String key) {
    if (TestNGConfigParams.getTestParams().containsKey(key)) {
      return Boolean.parseBoolean(TestNGConfigParams.getTestParams().get(key));
    }
    return prop.getBoolean(key);
  }

  protected boolean getPropertiesAsBoolean(String key, boolean defaultVal) {
    if (TestNGConfigParams.getTestParams().containsKey(key)) {
      return Boolean.parseBoolean(TestNGConfigParams.getTestParams().get(key));
    }
    return prop.getBoolean(key, defaultVal);
  }

  public DriverType getBrowser() {
    String browser = getPropertiesAsString("Browser");
    if (browser == null || browser.equals("chrome")) {
      return DriverType.CHROME;
    } else if (browser.equalsIgnoreCase("firefox")) {
      return DriverType.FIREFOX;
    } else if (browser.equalsIgnoreCase("ieexplorer")) {
      return DriverType.INTERNETEXPLORER;
    } else {
      throw new RuntimeException(
          "Browser Name Key value in Configuration.properties is not matched : " + browser);
    }

    //return browser
  }

  public String getEnv() {
    return System.getProperty(ENV, getPropertiesAsString("Env"));
  }

  public String getValueFor(String key){
    return prop.getString(key,"NOT_FOUND");
  }

  public boolean getHeadlessBrowser() {
    return Boolean.parseBoolean(getPropertiesAsString("Browser.headless"));
  }
}
