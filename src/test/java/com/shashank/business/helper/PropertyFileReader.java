package com.shashank.business.helper;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

@Config.Sources(value = {
    "file:src//main//resources//config.properties",
    "file:src//main//resources//${profile}.properties",
    "system:env"}
)
@LoadPolicy(LoadType.MERGE)
public interface PropertyFileReader extends Config {

  @Key("wait.least")
  int leastwait();

  @Key("wait.long")
  int longWait();

  @Key("wait.longest")
  int longestWait();

  @Key("db.name")
  String dbName();

  @Key("db.url")
  String dbURL();

  @Key("db.username")
  String dbUsername();

  @Key("db.password")
  String dbPassword();

  @Key("grid.enable")
  boolean isGridEnabled();

  @Key("grid.url")
  String gridURL();

  @Key("grid.host")
  String gridHost();

  @Key("aws.url")
  String awsURL();

  @Key("aws.username")
  String awsUsername();

  @Key("aws.password")
  String awsPassword();

  @Key("screenshot.enable")
  boolean takeScreenshot();

  @Key("kibana.url")
  String kibanaURL();

  @Key("kibana.username")
  String kibanaUsername();

  @Key("kibana.password")
  String kibanaPassword();

  @Key("app.url")
  String appUrl();
}
