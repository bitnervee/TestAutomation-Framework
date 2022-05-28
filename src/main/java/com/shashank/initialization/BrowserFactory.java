package com.shashank.initialization;

import com.shashank.framework.LogMe;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class BrowserFactory {
  private static final LogMe log = new LogMe(BrowserFactory.class.getSimpleName());
  private static final Supplier<WebDriver> CHROME_SUPPLIER = () -> {
    WebDriverManager.chromedriver().setup();
    return new ChromeDriver();
  };
  private static final Supplier<WebDriver> FIREFOX_SUPPLIER = () -> {
    WebDriverManager.firefoxdriver().setup();
    return new FirefoxDriver();
  };
  private static final Supplier<WebDriver> EDGE_SUPPLIER = () -> {
    WebDriverManager.edgedriver().setup();
    return new EdgeDriver();
  };
  private static final Supplier<WebDriver> SAFARI_SUPPLIER = () -> {
    WebDriverManager.safaridriver().setup();
    return new SafariDriver();
  };
  private static final Map<String, Supplier<WebDriver>> MAP = new HashMap<>();

  static {
    MAP.put("chrome", CHROME_SUPPLIER);
    MAP.put("firefox", FIREFOX_SUPPLIER);
    MAP.put("safari", SAFARI_SUPPLIER);
    MAP.put("edge", EDGE_SUPPLIER);
  }

  public void setUp(String browserType) {
    log.info("---------------------SETTING UP WEB DRIVER---------------------");
    if (Objects.isNull(PageDriver.getDriver())) {
      WebDriver driver = MAP.get(browserType).get();
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
