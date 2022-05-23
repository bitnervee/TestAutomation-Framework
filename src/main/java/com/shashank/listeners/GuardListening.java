package com.shashank.listeners;

import com.shashank.initialization.BrowserFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class GuardListening implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        BrowserFactory browserFactory = new BrowserFactory();
        System.err.println("Invoking the Browser");
        browserFactory.setUp();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        System.err.println("PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        System.err.println("FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        System.err.println("SKIPPED");
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        BrowserFactory browserFactory = new BrowserFactory();
        System.err.println("Tearing Down the Browser");
        browserFactory.tearDown();
    }
}
