package com.shashank.listeners;

import com.shashank.annotations.NeedWeb;
import com.shashank.framework.LogMe;
import com.shashank.initialization.BrowserFactory;
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
    log.info(String.format("Test [%s] Passed !", result.getName()));
    browserFactory.tearDown();
  }

  @Override
  public void onTestFailure(ITestResult result) {
    ITestListener.super.onTestFailure(result);
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
    log.info("---------------------FINISHED---------------------");
  }
}
