package com.shashank.tests.smoke;

import com.shashank.annotations.NeedWeb;
import com.shashank.annotations.Scenario;
import com.shashank.business.Configuration;
import com.shashank.initialization.PageDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

@Scenario(tester = TestInfo.tester, module = TestInfo.module, id = TestInfo.id, description = TestInfo.description)
public class SampleTest {

    private final Configuration configuration = Configuration.getInstance();

    @Test
    @NeedWeb()
    public void testRunOnChrome() throws InterruptedException {
        WebDriver driver = PageDriver.getDriver();
        driver.get(configuration.getAppUrl());
        Thread.sleep(6000);
    }

    @Test(enabled = false)
    @NeedWeb(browserType = "edge")
    public void testRunOnEdge() throws InterruptedException {
        WebDriver driver = PageDriver.getDriver();
        driver.get(configuration.getAppUrl());
        Thread.sleep(6000);
    }

}
