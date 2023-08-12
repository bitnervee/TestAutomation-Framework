package com.shashank.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

  private static ExtentReports reports;

  public static ExtentReports getInstance() {
    if (reports == null) {
      ExtentSparkReporter spark = new ExtentSparkReporter("spark");
      reports = new ExtentReports();

      spark.config().setDocumentTitle("Automation-Core report");
      spark.config().setEncoding("utf-8");
      spark.config().setTheme(Theme.DARK);
      spark.config().setReportName("My Own Report");

      reports.attachReporter(spark);
    }

    return reports;
  }
}
