package com.shashank.util;

import java.util.HashMap;
import java.util.Map;

public class TestNGConfigParams {

  private static volatile Map<String, String> params = new HashMap<>();

  public static synchronized Map<String, String> getTestParams() {
    return params;
  }

  public static synchronized void setTestParams(Map<String, String> inputParams) {
    params = inputParams;
  }
}
