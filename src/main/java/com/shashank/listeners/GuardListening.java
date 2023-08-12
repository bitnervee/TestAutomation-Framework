package com.shashank.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.shashank.annotations.NeedWeb;
import com.shashank.annotations.Scenario;
import com.shashank.framework.LogMe;
import com.shashank.initialization.BrowserFactory;
import com.shashank.reporting.ExtentManager;
import com.shashank.reporting.ExtentReport;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class GuardListening implements ITestListener {

  private final BrowserFactory browserFactory = new BrowserFactory();
  private final LogMe log = new LogMe(ITestListener.class.getSimpleName());

  @Override
  public void onStart(ITestContext context) {
    ITestListener.super.onStart(context);
    log.info("---------------------STARTING---------------------");
  }

  @Override
  public void onTestStart(ITestResult result) {
    ExtentTest test = ExtentManager.getInstance().createTest(result.getName());
    ExtentReport.setTest(test);
    Class<?> clazz = result.getMethod().getRealClass();
    String tester = clazz.getAnnotation(Scenario.class).tester();
    String id = clazz.getAnnotation(Scenario.class).id();
    String description = clazz.getAnnotation(Scenario.class).description();
    String module = clazz.getAnnotation(Scenario.class).module();
    ExtentReport.getTest().assignAuthor(tester);
    ExtentReport.getTest().info(id);
    ExtentReport.getTest().info(description);
    ExtentReport.getTest().assignCategory(module);
    ITestNGMethod m = result.getMethod();
    NeedWeb needWeb = m.getConstructorOrMethod().getMethod()
        .getAnnotation(NeedWeb.class);
    String browserToSpawn = needWeb.browserType();
    browserFactory.setUp(browserToSpawn);
    log.info(String.format("Starting execution for test method [%s] !", result.getName()));
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    ITestListener.super.onTestSuccess(result);
    ExtentReport.getTest().log(Status.PASS, String.format("Test [%s] Passed !", result.getName()));
    log.info(String.format("Test [%s] Passed !", result.getName()));
    browserFactory.tearDown();
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ITestListener.super.onTestFailure(result);
    ExtentReport.getTest().log(Status.FAIL, String.format("Test [%s] Failed !", result.getName()));
    log.error(String.format("Test [%s] Failed !", result.getName()));
    browserFactory.tearDown();
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    ITestListener.super.onTestSkipped(result);
    log.warn(String.format("Test [%s] Skipped !", result.getName()));
    browserFactory.tearDown();
  }

  @Override
  public void onFinish(ITestContext context) {
    ITestListener.super.onFinish(context);
    browserFactory.tearDown();
    ExtentManager.getInstance().flush();
    log.info("---------------------FINISHED---------------------");
  }
}
