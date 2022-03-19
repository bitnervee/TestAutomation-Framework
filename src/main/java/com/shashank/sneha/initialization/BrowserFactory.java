package com.shashank.sneha.initialization;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class BrowserFactory {

    public static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);

    WebDriver driver ;
    @BeforeClass
    public void setUp(){
        //String browser = System.getProperty("safari");
        logger.debug("Selecting selenium browser using" );
        WebDriverManager.safaridriver().setup();
        driver = new SafariDriver();

        PageDriver.setDriver(driver);
    }



    @AfterClass
    public void tearDown(){
        PageDriver.getDriver().quit();
    }
}
