package com.shashank;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.sql.DriverManager;

public class SetupAndTearDown {

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();
    }

    @AfterMethod
    public void tearDown(){
        WebDriverManager.chromedriver().quit();
    }
}
