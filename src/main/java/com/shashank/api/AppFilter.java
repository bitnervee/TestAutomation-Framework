package com.shashank.api;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.util.HashMap;
import java.util.Map;

public class AppFilter implements Filter {

  private String xsrfToken = null;

  public Response filter(FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec, FilterContext ctx) {
    if (xsrfToken != null) {
      Map<String, Object> payload = new HashMap<>();
      payload.put("_G_XSRF", this.xsrfToken);

      requestSpec.formParams(payload);
      requestSpec.header("-g_xsrf", this.xsrfToken);

      payload.put("_", System.currentTimeMillis());
      requestSpec.queryParams(payload);
    }

    Response response = ctx.next(requestSpec, responseSpec);

    if (xsrfToken == null) {
      xsrfToken = response.cookie("_X_XSRF");
    }
    return response;
  }
}
