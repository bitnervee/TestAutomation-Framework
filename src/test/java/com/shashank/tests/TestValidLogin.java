package com.shashank.tests;

import com.shashank.annotations.NeedWeb;
import com.shashank.initialization.PageDriver;
import com.shashank.pages.LoginPage;
import com.shashank.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class TestValidLogin {

    @Test
    @NeedWeb(browserType = "safari")
    public void runTest(){
        LoginPage loginPage = new LoginPage();
        ProductPage productPage = new ProductPage();
        WebDriver driver = PageDriver.getDriver();
        driver.get("https://www.saucedemo.com");
        loginPage.LoginIntoApplication("standard_user","secret_sauce");
        productPage.verifyProductPageDisplayed();
    }
}
