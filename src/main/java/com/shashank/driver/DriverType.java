package com.shashank.driver;

public enum DriverType {

  FIREFOX("Firefox"),
  CHROME("Chrome"),
  INTERNETEXPLORER("Internet Explorer");

  private final String browser;

  DriverType(String browser) {
    this.browser = browser;
  }

  @Override
  public String toString() {
    return browser;
  }
}
