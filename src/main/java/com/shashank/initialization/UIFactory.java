package com.shashank.initialization;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public enum UIFactory {

  CHROME {
    @Override
    public WebDriver createDriver() {
      WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
      return new ChromeDriver(getOptions());
    }

    @Override
    public ChromeOptions getOptions() {
      ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions.addArguments(START_MAXIMIZED);
      return chromeOptions;
    }
  }, FIREFOX {
    @Override
    public WebDriver createDriver() {
      WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
      return new FirefoxDriver(getOptions());
    }

    @Override
    public FirefoxOptions getOptions() {
      FirefoxOptions firefoxOptions = new FirefoxOptions();
      firefoxOptions.addArguments(START_MAXIMIZED);
      return firefoxOptions;
    }
  }, SAFARI {
    @Override
    public WebDriver createDriver() {
      WebDriverManager.getInstance(DriverManagerType.SAFARI).setup();

      return new SafariDriver(getOptions());
    }

    @Override
    public SafariOptions getOptions() {
      SafariOptions safariOptions = new SafariOptions();
      safariOptions.setAutomaticInspection(false);
      return safariOptions;
    }
  };

  private static final String START_MAXIMIZED = "--start-maximized";

  public abstract WebDriver createDriver();

  public abstract MutableCapabilities getOptions();
}
