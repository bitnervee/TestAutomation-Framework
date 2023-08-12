package com.shashank.pages;

import com.shashank.initialization.PageDriver;
import com.shashank.support.UserActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  @FindBy(id = "user-name")
  public WebElement userNameField;
  @FindBy(id = "password")
  public WebElement passwordField;
  @FindBy(id = "login-button")
  public WebElement loginButton;
  WebDriver driver;

  public LoginPage() {
    driver = PageDriver.getDriver();
    PageFactory.initElements(driver, this);
  }


  public void LoginIntoApplication(String userName, String passWord) {
    UserActions userActions = new UserActions();
    userActions.sleep(5);
    userActions.sendKeys(userNameField, userName);
    userActions.sendKeys(passwordField, passWord);
    userActions.sleep(5);
    userActions.click(loginButton);
  }
}
