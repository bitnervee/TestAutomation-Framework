package com.shashank.util;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.ResponseBody;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JsonUtils {

  private JsonUtils() {
  }

  public static List<String> parse(ResponseBody<?> body,String path,String filedName, Function<Map<String,Object>,String> mapper){
    List<Map<String, Object>> reference = parse(body, path , filedName);
    return reference.stream().map(mapper).collect(Collectors.toList());
  }

  public static List<Map<String,Object>> parse(ResponseBody<?> body, String path, String fieldName){
    Filter filter = Filter.filter(Criteria.where(fieldName).empty(false));
    return parse(body,path,filter);
  }

  public static List<String> parse(ResponseBody<?> body,String path, Filter filter,Function<Map<String,Object>,String> mapper){
    List<Map<String,Object>> reference = parse(body,path,filter);
    return reference.stream().map(mapper).collect(Collectors.toList());
  }

  public static List<Map<String,Object>> parse(ResponseBody<?> body,String path, Filter filter){
    return parse(body.print(), path,filter);
  }

  public static List<Map<String,Object>> parseText(String body, String path, String fieldName){
    Filter filter = Filter.filter(Criteria.where(fieldName).exists(true));
    return parse(body,path,filter);
  }

  public static List<String> parseText(String body, String path, Filter filter,
      Function<Map<String, Object>, String> mapper) {
    List<Map<String, Object>> reference = parse(body, path, filter);
    return reference.stream().map(mapper).collect(Collectors.toList());
  }

  public static List<Map<String,Object>> parseText(String body, String path, Filter filter){
    return parse(body, path, filter);
  }

  public static String parseText(String body, String path){
    return JsonPath.parse(body).read(path).toString();
  }

  public static List<Map<String,Object>> parse(String body,String path,Filter filter){
    return JsonPath.parse(body).read(path,filter);
  }
}
