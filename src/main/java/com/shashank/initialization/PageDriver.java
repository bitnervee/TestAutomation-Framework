package com.shashank.initialization;

import org.openqa.selenium.WebDriver;

public final class PageDriver {

    public static final ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return webDriver.get();
    }

    public static void setDriver(WebDriver driver){
        webDriver.set(driver);
    }
}
