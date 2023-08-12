package com.shashank.api;

import static com.shashank.util.StepLogger.step;
import static org.hamcrest.CoreMatchers.equalTo;

import com.shashank.util.Configuration;
import io.restassured.response.Response;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass implements IHookable {

  private static final Configuration configuration = Configuration.getInstance();
  private static final String PASSWORD = Configuration.CONFIG;

  private String user = "";

  @BeforeMethod(alwaysRun = true)
  public final void setUpClient() {
    String user = "";
    HttpFilter.newSpec();
  }

  @AfterMethod(alwaysRun = true)
  public final void cleanUpClient() {
    HttpFilter.close();
  }

  @Override
  public void run(IHookCallBack callBack, ITestResult testResult) {
    callBack.runTestMethod(testResult);
  }

  public final void login() {
    step("Login into Pvai", ()->{
      Response response = HttpFilter.getSpec().get("/profile");
      response.then().assertThat().body("status",equalTo(true));
    }).snap();

    step("get with query params request sample", ()->{
      Response response = HttpFilter.getSpec()
          .queryParam("_", System.currentTimeMillis())
          .queryParam("sgid", "GROUP")
          .get("/endpoint");
      response.then().assertThat().body("status", equalTo(true));
    }).snap();

    step("post request sample",()->{
      HttpFilter.getSpec().formParam("username",user)
          .formParam("password",PASSWORD)
          .post("/postendpoint");
    }).snap();
  }
}
