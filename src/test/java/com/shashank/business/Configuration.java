package com.shashank.business;

import com.shashank.business.helper.PropertyFileReader;
import org.aeonbits.owner.ConfigFactory;

public final class Configuration {

  public static Configuration instance = null;
  private final PropertyFileReader propertyFileReader = ConfigFactory.create(
      PropertyFileReader.class);

  private Configuration() {

  }

  public static Configuration getInstance() {
    if (instance == null) {
      instance = new Configuration();
    }
    return instance;
  }

  public int getLeastWait() {
    return propertyFileReader.leastwait();
  }

  public int getLongWait() {
    return propertyFileReader.longWait();
  }

  public int getLongestWait() {
    return propertyFileReader.longestWait();
  }

  public String getDBName() {
    return propertyFileReader.dbName();
  }

  public String getDBUrl() {
    return propertyFileReader.dbURL();
  }

  public String getDBUsername() {
    return propertyFileReader.dbUsername();
  }

  public String getDBPassword() {
    return propertyFileReader.dbPassword();
  }

  public String getAWSUrl() {
    return propertyFileReader.awsURL();
  }

  public String getAWSUsername() {
    return propertyFileReader.awsUsername();
  }

  public String getAWSPassword() {
    return propertyFileReader.awsPassword();
  }

  public boolean isSeleniumGridEnabled() {
    return propertyFileReader.isGridEnabled();
  }

  public String seleniumGridUrl() {
    return propertyFileReader.gridURL();
  }

  public String seleniumGridHost() {
    return propertyFileReader.gridHost();
  }

  public boolean isScreenshotEnabled() {
    return propertyFileReader.takeScreenshot();
  }

  public String getKibanaUrl() {
    return propertyFileReader.kibanaURL();
  }

  public String getKibanaUsername() {
    return propertyFileReader.kibanaUsername();
  }

  public String getKibanaPassword() {
    return propertyFileReader.kibanaPassword();
  }

  public String getAppUrl() {
    return propertyFileReader.appUrl();
  }
}