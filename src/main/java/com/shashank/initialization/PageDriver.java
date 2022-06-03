package com.shashank.initialization;

import org.openqa.selenium.WebDriver;

public final class PageDriver {

  public static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
  public static PageDriver instance = null;

  private PageDriver(){

  }

  public static PageDriver getInstance(){
    if (instance == null){
      instance = new PageDriver();
    }
    return instance;
  }

  public WebDriver getCurrentDriver(){
    return webDriver.get();
  }

  public static WebDriver getDriver() {
    return getInstance().getCurrentDriver();
  }

  public void setDriver(WebDriver driver) {
    webDriver.set(driver);
  }

  public void removeDriver() {
    webDriver.remove();
  }

}
