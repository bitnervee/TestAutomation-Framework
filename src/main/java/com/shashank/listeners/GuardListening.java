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

    @Override
    public void onTestStart(ITestResult result) {
        ITestNGMethod m = result.getMethod();
        NeedWeb needWeb = m.getConstructorOrMethod().getMethod()
            .getAnnotation(NeedWeb.class);
        String browserToSpawn = needWeb.browserType();
        LogMe.info(String.format("Invoking the %s Browser", browserToSpawn));
        browserFactory.setUp(browserToSpawn);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        LogMe.info(String.format("Test [%s] Passed !", result.getTestName()));
        browserFactory.tearDown();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        LogMe.warn(String.format("Test [%s] Failed !", result.getTestName()));
        browserFactory.tearDown();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        LogMe.info(String.format("Test [%s] Skipped !", result.getTestName()));
        browserFactory.tearDown();
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        LogMe.info("Execution Finished !");
        browserFactory.tearDown();
    }
}
