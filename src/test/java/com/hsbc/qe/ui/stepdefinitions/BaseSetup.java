package com.hsbc.qe.ui.stepdefinitions;

import com.hsbc.qe.ui.config.Configuration;
import com.hsbc.qe.ui.config.ConfigurationManager;
import com.hsbc.qe.ui.context.TestContext;
import com.hsbc.qe.ui.webdriver.DriverManagerUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import java.lang.reflect.Field;

public class BaseSetup {

    public static Configuration configuration;
    private static final String UI_PAGE_OBJECTS_PACKAGE = "com.hsbc.qe.ui.pageobjects.";
    TestContext testContext;
    WebDriver driver;
    DriverManagerUtils driverManagerUtils;

    public BaseSetup(TestContext testContext) {
        this.testContext = testContext;
        driver = testContext.getDriverManager().getDriver();
        configuration =  ConfigurationManager.getConfiguration();
        driverManagerUtils = new DriverManagerUtils(driver);
    }

    public WebElement loadWebElement(String elementName, String pageClassName) {
        return loadAndFindPageElement(elementName, pageClassName);
    }

    private WebElement loadAndFindPageElement(String locatorName, String pageClassName) {
       WebElement element = null;
        Class<?> loadClass = null;
        try {
            loadClass = Class.forName(UI_PAGE_OBJECTS_PACKAGE + pageClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        PageFactory.initElements(driver, loadClass);
        Field field = null;
        try {
            field = loadClass.getDeclaredField(locatorName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        try {
            element = (WebElement) field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return element;
    }
}
