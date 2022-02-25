package com.hsbc.qe.ui.stepdefinitions;

import com.hsbc.qe.ui.context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;

public class PrebuiltWaitSteps extends BaseSetup {

    WebDriver driver;
    TestContext testContext;

    public PrebuiltWaitSteps(TestContext context) {
        super(context);
        this.testContext = context;
        driver = testContext.getDriverManager().getDriver();
    }

    /** Wait for an element to be present for a specific period of time */
    @When("^I wait for element (.*) to be present on the (.*) page$")
    public void waitForElementPresent(String elementName,String pageClassName) throws Exception
    {
        driverManagerUtils.waitForElementPresent(loadWebElement(elementName, pageClassName));
    }

    /**Wait for an element to be visible for a specific period of time */
    @When("^I wait element (.*?) to be visible on the (.*) page$$")
    public void waitForElementVisible(String elementName, String pageClassName) throws Exception
    {
        driverManagerUtils.waitForElementVisible(loadWebElement(elementName, pageClassName));
    }
}
