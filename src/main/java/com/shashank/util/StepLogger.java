package com.shashank.util;

import com.shashank.framework.LogMe;
import com.shashank.initialization.PageDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class StepLogger {

  private static final LogMe log = new LogMe(StepLogger.class.getName());
  private String stepDescription;
  private StepDefinition stepDefinition;
  private boolean silence;

  public StepLogger() {
    silence = false;
  }

  public static StepLogger step(String stepDescription, StepDefinition stepDefinition) {
    return new StepLogger().stepDetails(stepDescription, stepDefinition);
  }

  private static void narrateStepToConsole(String desc) {
    ITestResult result = Reporter.getCurrentTestResult();
    if (result == null) {
      return;
    }

    String msg = String.format("Executing step [%s] in testcase [%s]", desc,
        result.getMethod().getQualifiedName());
    log.info(msg);
  }

  private static List<String> getFailedStepList() {
    if (Reporter.getCurrentTestResult().getAttribute("failedSteps") != null) {
      return (List<String>) Reporter.getCurrentTestResult().getAttribute("failedSteps");
    } else {
      return new ArrayList<>();
    }
  }

  private static void attachTimeStampAndUrlInSS(BufferedImage image) {
    Map<TextAttribute, Object> attribute = new HashMap<>();
    Graphics g = image.getGraphics();
    Font currentFont = g.getFont();
    attribute.put(TextAttribute.FAMILY, currentFont.getFamily());
    attribute.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD);
    attribute.put(TextAttribute.SIZE, currentFont.getSize() * 1.5);
    g.setFont(Font.getFont(attribute));
    g.setColor(Color.BLACK);
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH::mm");
    LocalDateTime now = LocalDateTime.now();
    g.drawString(dtf.format(now), image.getWidth() - 260, image.getHeight() - 40);
    g.drawString("google.com", image.getWidth() - 260, image.getHeight() - 20);
    g.dispose();
  }

  private static byte[] captureScreenshot() {
    RemoteWebDriver rwd = PageDriver.getYourDriver();
    return rwd.getScreenshotAs(OutputType.BYTES);
  }

  private StepLogger stepDetails(String stepDescription, StepDefinition stepDefinition) {
    this.stepDescription = stepDescription;
    this.stepDefinition = stepDefinition;
    return this;
  }

  public StepLogger withSoftAssertion() {
    this.silence = true;
    return this;
  }

  public void snap() {
    snap(false);
  }

  public void snap(boolean captureScreenshot) {
    ITestResult result = Reporter.getCurrentTestResult();
    result.setAttribute("currentStep", stepDescription);
    try {
      narrateStepToConsole(stepDescription);
      Allure.step(stepDescription, () -> {
        try {
          stepDefinition.run();
        } finally {
          if (captureScreenshot) {
            attachScreenshot();
          }
          AllureLifecycle alc = Allure.getLifecycle();
          Object textObject = result.getAttribute("actualResult");
          if (null != textObject) {
            String attachment = alc.prepareAttachment(stepDescription, "", "txt");
            alc.writeAttachment(attachment,
                new ByteArrayInputStream(textObject.toString().getBytes(StandardCharsets.UTF_8)));
            Reporter.getCurrentTestResult().setAttribute("actualResult", null);
          }
        }
      });
    } catch (AssertionError e) {
      if (!silence) {
        throw e;
      }
      List<String> failedStepList = getFailedStepList();
      failedStepList.add(stepDescription);
      result.setAttribute("failedSteps", failedStepList);
      result.setStatus(ITestResult.FAILURE);
      Reporter.setCurrentTestResult(result);
    }
  }

  private void attachScreenshot() {
    AllureLifecycle alc = Allure.getLifecycle();
    String attachment = alc.prepareAttachment(stepDescription, "", "png");
    try {
      BufferedImage image = ImageIO.read(new ByteArrayInputStream(captureScreenshot()));
      attachTimeStampAndUrlInSS(image);
      alc.writeAttachment(attachment, new ByteArrayInputStream(toByteArray(image, "png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SneakyThrows
  public byte[] toByteArray(BufferedImage bi, String format) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bi, format, baos);
    byte[] bytes = baos.toByteArray();
    return bytes;
  }

  public void captureFullPageScreenShot(RemoteWebDriver driver, String stepDescription) {
    try {
      Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
          .takeScreenshot(driver);
      AllureLifecycle alc = Allure.getLifecycle();
      String attachment = alc.prepareAttachment(stepDescription, "", "png");
      alc.writeAttachment(attachment,
          new ByteArrayInputStream(toByteArray(screenshot.getImage(), "png")));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
