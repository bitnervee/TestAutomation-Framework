package com.shashank.reporting;

import com.aventstack.extentreports.ExtentTest;

public class ExtentReport {

    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    public static ExtentTest getTest(){
        return extentTest.get();
    }

    public static void setTest(ExtentTest test){
        extentTest.set(test);
    }
}
