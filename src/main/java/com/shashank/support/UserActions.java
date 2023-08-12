package com.shashank.support;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;

public class UserActions {

  public UserActions() {

  }

  public void sleep(long intervalInSeconds) {
    try {
      TimeUnit.SECONDS.sleep(intervalInSeconds);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void sendKeys(WebElement element, String key) {
    element.sendKeys(key);
    sleep(2);
  }

  public void click(WebElement element) {
    element.click();
  }
}
