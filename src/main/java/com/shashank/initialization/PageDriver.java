package com.shashank.initialization;

import com.google.common.base.Preconditions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public final class PageDriver {

  public static final ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<>();
  public static PageDriver instance = null;
  //private static final ThreadLocal<RemoteWebDriver> driverThreadLocal = new ThreadLocal<>();

  private PageDriver() {

  }

  public static PageDriver getInstance() {
    if (instance == null) {
      instance = new PageDriver();
    }
    return instance;
  }

  public static WebDriver getDriver() {
    return getInstance().getCurrentDriver();
  }

  public void setDriver(RemoteWebDriver driver) {
    webDriver.set(driver);
  }

  public static RemoteWebDriver getYourDriver() {
    Preconditions.checkNotNull(webDriver.get(), "No Valid web-driver found for current thread.");
    return webDriver.get();
  }

  public WebDriver getCurrentDriver() {
    return webDriver.get();
  }

  public void removeDriver() {
    webDriver.remove();
  }

}
