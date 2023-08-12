package com.shashank.pages;

import com.shashank.initialization.PageDriver;
import com.shashank.support.UserActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

  public WebDriver driver;

  @FindBy(xpath = "//span[contains(text(),'Products')]")
  public WebElement productPage;

  public ProductPage() {
    driver = PageDriver.getDriver();
    PageFactory.initElements(driver, this);
  }

  public void verifyProductPageDisplayed() {
    UserActions userActions = new UserActions();
    userActions.sleep(5);
    productPage.isDisplayed();
  }
}
