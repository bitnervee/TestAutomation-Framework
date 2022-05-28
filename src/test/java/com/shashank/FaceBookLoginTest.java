package com.shashank;

import com.shashank.annotations.NeedWeb;
import com.shashank.initialization.PageDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class FaceBookLoginTest {

  @Test
  @NeedWeb(browserType = "chrome")
  public void testRunOnChrome() throws InterruptedException {
    WebDriver driver = PageDriver.getDriver();
    System.out.println(driver);
    driver.get("https://www.facebook.com");
    Thread.sleep(6000);
  }

  @Test(enabled = false)
  @NeedWeb(browserType = "edge")
  public void testRunOnEdge() throws InterruptedException {
    WebDriver driver = PageDriver.getDriver();
    System.out.println(driver);
    driver.get("https://www.facebook.com");
    Thread.sleep(6000);
  }

}
