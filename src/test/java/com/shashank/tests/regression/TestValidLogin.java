package com.shashank.tests.regression;

import static com.shashank.util.StepLogger.step;

import com.shashank.annotations.NeedWeb;
import com.shashank.annotations.Scenario;
import com.shashank.initialization.PageDriver;
import com.shashank.pages.LoginPage;
import com.shashank.pages.ProductPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

@Scenario(tester = Information.tester, module = Information.module, id = Information.id, description = Information.description)
public class TestValidLogin {

  @Test
  @NeedWeb()
  public void runTest() {
    LoginPage loginPage = new LoginPage();
    ProductPage productPage = new ProductPage();
    WebDriver driver = PageDriver.getDriver();
    step("Invoke Url", () -> driver.get("https://www.saucedemo.com")).snap();

    step("Login to Application with valid username and password",
        () -> loginPage.LoginIntoApplication("standard_user", "secret_sauce")).snap(true);
    step("Step logging", productPage::verifyProductPageDisplayed).snap(true);
  }
}
