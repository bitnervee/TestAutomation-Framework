package com.shashank.initialization;

import com.shashank.driver.DriverType;
import com.shashank.framework.LogMe;
import com.shashank.util.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;

public class DriverManager {

  private static final LogMe logs = new LogMe(DriverManager.class.getName());
  private static DriverType driverType;
  private RemoteWebDriver driver;

  public DriverManager() {
    driverType = Configuration.getInstance().getBrowser();
  }

  public static RemoteWebDriver createDriver(DriverType driverType) {
    boolean runRemotely = false;
    MutableCapabilities options;
    RemoteWebDriver driver = null;
    switch (driverType) {
      case FIREFOX:
        if (runRemotely) {
          options = new FirefoxOptions();
          if (true) {
            options.merge(constructCloudCapabilities());
          }
          driver = new RemoteWebDriver(options);
        } else {
          WebDriverManager.firefoxdriver().arch32().setup();
          driver = new FirefoxDriver();
        }
        break;
      case CHROME:
        driver = createDriverForChromeBrowser();
        break;
    }
    driver.manage().window().maximize();
    return driver;
  }

  private static String testName() {
    String testName = "UNKNOWN";
    ITestResult result = Reporter.getCurrentTestResult();
    if (result != null) {
      String c = result.getMethod().getTestClass().getRealClass().getName();
      String m = result.getMethod().getMethodName();
      testName = c + "." + m;
    }
    return testName;
  }

  private static DesiredCapabilities constructCloudCapabilities() {
    DesiredCapabilities options = new DesiredCapabilities();
    options.setCapability("browser", "IE");
    options.setCapability("os", "Windows");
    options.setCapability("os_version", "10");
    options.setCapability("browser_version", "11.0");
    options.setCapability("browserstack.local", "true");
    options.setCapability("browserstack.console", "info");
    options.setCapability("build", Configuration.getInstance().getEnv());
    options.setCapability("name", testName());
    options.setCapability("browserstack.selenium_version", "3.14.0");
    options.setCapability("browserstack.ie.arch", "x32");
    options.setCapability("resolution", "1280*800");
    return options;
  }

  private static URL gridUrl() {
    try {
      String url = "";
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  private static RemoteWebDriver createDriverForChromeBrowser() {
    RemoteWebDriver driver;
    boolean runRemotely = false;
    ChromeOptions options = new ChromeOptions();
    options.addArguments("chrome.switches", "--disable-extensions", "--ignore-certificate-errors",
        "--ignore--ssl-errors=yes");
    if (Configuration.getInstance().getHeadlessBrowser()) {
      options.setHeadless(true);
    }

    if (runRemotely) {
      driver = new RemoteWebDriver(options);
    } else {
      WebDriverManager.chromedriver().arch32().setup();
      driver = new ChromeDriver(options);
    }
    return driver;
  }

  public static RemoteWebDriver ieMode() {
    WebDriverManager.iedriver().driverVersion("4.2.0.0").arch32().setup();

    InternetExplorerOptions ieOptions = new InternetExplorerOptions();
    Map<String, Object> map = new HashMap<>();
    map.put(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
    map.put("ie.edgechromium", true);
    map.put("ie.edgepath", "IEDRIVERPATH");
    ieOptions.setCapability("se:ieOptions", map);

    InternetExplorerDriver driver = new InternetExplorerDriver(ieOptions);
    driver.manage().window().maximize();
    return driver;
  }

  public RemoteWebDriver getDriver() {
    if (driver == null) {
      driver = createDriver();
    }
    return driver;
  }

  RemoteWebDriver createDriver() {
    return createDriver(driverType);
  }

  public RemoteWebDriver launchChrome() {
    if (driver == null) {
      driver = createDriverForChromeBrowser();
    }
    return driver;
  }
}
