package com.hsbc.qe.ui.webdriver;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.util.ArrayList;
import java.util.List;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final List<WebDriver> storedDrivers = new ArrayList<>();

    public DriverManager() {}

    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }

    public void addDriver(WebDriver driver) {
        storedDrivers.add(driver);
        DriverManager.driver.set(driver);
    }

    public static void quitDriver() {
        DriverManager.driver.get().quit();
        driver.remove();
    }

    public static void destroyDriver() {
        for (WebDriver driver : storedDrivers) {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public String getInfo() {
        Capabilities cap = ((RemoteWebDriver) getDriver()).getCapabilities();
        String browserName = cap.getBrowserName();
        String platform = cap.getPlatformName().toString();
        String version = cap.getBrowserVersion();
        return String.format("browser: %s v: %s platform: %s", browserName, version, platform);
    }
}
