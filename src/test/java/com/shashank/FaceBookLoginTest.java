package com.shashank;

import com.shashank.sneha.initialization.BrowserFactory;
import com.shashank.sneha.initialization.PageDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class FaceBookLoginTest {

    WebDriver wait ;
    WebDriver driver;

    @Test
    public void testRun() throws InterruptedException {
        WebDriver driver = PageDriver.getDriver();
        System.out.println(driver);
        //wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.facebook.com");
        Thread.sleep(6000);
        //driver.findElement(By.xpath("")).click();
        driver.quit();
    }

}
