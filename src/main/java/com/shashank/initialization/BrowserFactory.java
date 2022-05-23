package com.shashank.initialization;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserFactory{

    public static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);

    WebDriver driver = null;
    public void setUp(){
        String browser = System.getProperty("browser","safari");
        logger.debug("Selecting selenium browser using" );
        if (browser.contains("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if (browser.contains("safari")){
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }else if (browser.contains("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }


        PageDriver.setDriver(driver);
    }

    public void tearDown(){
        PageDriver.getDriver().quit();
    }
}
