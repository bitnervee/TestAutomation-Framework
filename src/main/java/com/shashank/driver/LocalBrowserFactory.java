package com.shashank.driver;

import com.shashank.framework.LogMe;

public class LocalBrowserFactory {

  private static final LogMe logs = new LogMe(LocalBrowserFactory.class.getName());

  public LocalBrowserFactory() {
    logs.info(getClass().getName() + " instantiated and wired in.");
  }


}
