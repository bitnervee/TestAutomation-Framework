package com.shashank.initialization;

import com.shashank.framework.LogMe;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Objects;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BrowserFactory {

  public static final LogMe log = new LogMe(BrowserFactory.class.getSimpleName());

  WebDriver driver = null;

  public void setUp(String browserType) {
    log.info("---------------------SETTING UP WEB DRIVER---------------------");
    if (Objects.isNull(PageDriver.getDriver())) {
      if (browserType.contains("chrome")) {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        log.info("---------------------Chrome Browser Spawned---------------------");
      } else if (browserType.contains("safari")) {
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();
        log.info("---------------------Safari Browser Spawned---------------------");
      } else if (browserType.contains("firefox")) {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        log.info("---------------------Firefox Browser Spawned---------------------");
      } else {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        log.info("---------------------Edge Browser Spawned---------------------");
      }
      PageDriver.setDriver(driver);
      log.info("---------------------WEB DRIVER SET UP DONE---------------------");
    } else {
      log.error("---------------------ERROR SETTING UP WEB DRIVER---------------------");
    }
  }

  public void tearDown() {
    if (Objects.nonNull(PageDriver.getDriver())) {
      PageDriver.getDriver().quit();
      PageDriver.removeDriver();
      log.info("---------------------QUITTING WEB DRIVER---------------------");
    }
  }
}
