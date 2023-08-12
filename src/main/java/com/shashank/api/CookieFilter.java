package com.shashank.api;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.util.HashMap;
import java.util.Map;

public class CookieFilter implements Filter {

  private final Map<String, String> cookieStore = new HashMap<>();

  public CookieFilter() {
  }

  public Response filter(FilterableRequestSpecification requestSpec,
      FilterableResponseSpecification responseSpec,
      FilterContext ctx) {
    requestSpec.cookies(cookieStore);
    Response response = ctx.next(requestSpec, responseSpec);
    cookieStore.putAll(response.getCookies());
    return response;
  }
}
