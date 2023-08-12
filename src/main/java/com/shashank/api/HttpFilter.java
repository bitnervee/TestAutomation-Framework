package com.shashank.api;

import com.shashank.util.Configuration;
import com.shashank.util.JsonUtils;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;

public class HttpFilter {

  private static final ThreadLocal<AppFilter> appFilter = new ThreadLocal<>();
  private static final ThreadLocal<CookieFilter> cookieFilter = new ThreadLocal<>();
  private static final ThreadLocal<String> url = new ThreadLocal<>();

  private HttpFilter() {
  }

  public static Map<String, ?> getTimeStamp() {
    Map<String, Object> payload = new HashMap<>();
    payload.put("_", System.currentTimeMillis());
    return payload;
  }

  public static RequestSpecification getSpec() {
    return createBaseRequest(url.get())
        .filter(cookieFilter.get())
        .filter(appFilter.get());
  }

  public static void close() {
    appFilter.set(null);
    cookieFilter.set(null);
    url.set(null);
  }

  public static RequestSpecification newSpec() {
    appFilter.set(new AppFilter());
    cookieFilter.set(new CookieFilter());
    url.set(Configuration.getInstance().getEnv());
    RequestSpecification temp = createBaseRequest(url.get())
        .filter(cookieFilter.get())
        .filter(appFilter.get());
    temp.get();
    return temp;
  }

  public static RequestSpecification newSpec(String url) {
    appFilter.set(new AppFilter());
    cookieFilter.set(new CookieFilter());
    return createBaseRequest(url)
        .filter(cookieFilter.get())
        .filter(appFilter.get());
  }

  private static RequestSpecification createBaseRequest(String baseUrl) {
    RequestSpecification specification = RestAssured.given().urlEncodingEnabled(true)
        .baseUri(baseUrl).config(RestAssuredConfig.newConfig()
            .sslConfig(new SSLConfig().relaxedHTTPSValidation().allowAllHostnames())
            .redirect(RedirectConfig.redirectConfig().followRedirects(false)));

    specification.filter(new AllureRestAssured());
    return specification.log().ifValidationFails();
  }

  private static String getAccessToken() {
    String realName = Configuration.getInstance().getValueFor("realmName");
    String path = String.format("/auth/realms/%s/protocol/openid-connect/token",realName);
    Map<String,?> mapParams = keyClockFilter();
    Response response = HttpFilter.getSpec()
        .header("Content-Type","application/x-WWW-form-urlencoded")
        .formParams(mapParams)
        .post(path);
    response.then().assertThat().statusCode(HttpStatus.SC_OK);
    return JsonUtils.parseText(response.body().prettyPrint(),"$['access_token']");
  }

  public static Map<String,?> keyClockFilter(){
    Configuration configuration = Configuration.getInstance();
    String userName = "User";
    String passWord = "password";
    String clientId = "clientID";

    Map<String,Object> formData = new HashMap<>();
    formData.put("grant_type","password");
    formData.put("username",userName);
    formData.put("password", passWord);
    formData.put("scope", "openid");
    formData.put("client_id",clientId);
    return formData;
  }
}
