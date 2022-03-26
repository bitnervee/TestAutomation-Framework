package com.shashank;

import com.shashank.initialization.BrowserFactory;
import com.shashank.initialization.PageDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class FaceBookLoginTest extends BrowserFactory{

    WebDriver wait ;
    WebDriver driver;

    @Test
    public void testRun() throws InterruptedException {
        driver = PageDriver.getDriver();
        System.out.println(driver);
        //wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.facebook.com");
        Thread.sleep(6000);
        //driver.findElement(By.xpath("")).click();
        driver.quit();
    }

}
