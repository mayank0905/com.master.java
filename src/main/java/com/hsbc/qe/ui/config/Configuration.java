package com.hsbc.qe.ui.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

@LoadPolicy(LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:general.properties",
        "classpath:local.properties",
        "classpath:db.properties",
        "classpath:grid.properties"})
public interface Configuration extends Config {

    @Key("baseurl")
    String baseUrl();

    @Key("browsername")
    String browserName();

    @Key("isheadless")
    boolean isHeadLess();

    @Key("gridURL")
    String gridURL();

    @Key("target")
    String target();

    @Key("browser")
    String browser();

    @Key("headless")
    Boolean headless();

    @Key("url.base")
    String url();

    @Key("timeout")
    int timeout();

    @Key("grid.url")
    String gridUrl();

    @Key("grid.port")
    String gridPort();

    @Key("db.host")
    String dbHost();

    @Key("db.port")
    String dbPort();

    @Key("db.name")
    String dbName();

    @Key("db.user")
    String dbUser();

    @Key("db.password")
    String dbPassword();


}
