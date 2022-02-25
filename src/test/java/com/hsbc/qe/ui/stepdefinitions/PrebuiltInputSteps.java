package com.hsbc.qe.ui.stepdefinitions;

import com.hsbc.qe.common.utils.DateUtils;
import com.hsbc.qe.ui.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrebuiltInputSteps extends BaseSetup {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrebuiltBrowserSteps.class);
    TestContext testContext;
    public PrebuiltInputSteps(TestContext context) {
        super(context);
        this.testContext = context;
    }

    @When("I click the {string} on the {string} page")
    public void iClickTheElementOnThePage(String elementName, String pageClassName) {
        driverManagerUtils.clickElement(driver, loadWebElement(elementName, pageClassName));
    }

    @When("I click the {string} using java script executor on the {string} page")
    public void ClickJSElement(String elementName, String pageClassName) throws Exception
    {
        driverManagerUtils.clickJSElement(loadWebElement(elementName, pageClassName));
    }

    @When("I double click the {string} on the {string} page")
    public void iDoubleClickTheElementOnThePage(String elementName, String pageClassName) {
        driverManagerUtils.doubleClickTheElement(loadWebElement(elementName, pageClassName));
    }

    @When("I enter the value {string} into the {string} on the {string} page")
    public void iEnterTheValueInToTheFieldOnThePage(String valueToEnter, String elementName, String pageClassName) {
        WebElement element = loadWebElement(elementName, pageClassName);
        driverManagerUtils.enterText(driver, element, valueToEnter);
    }

    @When("I select the {string} option type with value {string} from the {string} dropdown menu on the page {string}")
    public void iSelectFromDropDown(String optionType, String optionValue, String elementName, String pageClassName) {
        WebElement element = loadWebElement(elementName, pageClassName);
        driverManagerUtils.selectOptionFromDropdown(element, optionType, optionValue);
    }

    @When("I accept the alert")
    public void acceptAlert(){
        driverManagerUtils.acceptTheAlert();
    }

    @When("I dismiss the alert")
    public void dismissAlert(){
        driverManagerUtils.dismissTheAlert();
    }

    @When("I enter the date as today with {int} days into the {string} on the {string} page")
    public void enterDate(int days, String elementName, String pageClassName) {
        String dateFormatPattern = "yyyy-MM-dd";
        WebElement element = loadWebElement(elementName, pageClassName);
        String dateValue = DateUtils.getDate(days, dateFormatPattern);
        driverManagerUtils.enterText(driver, element, dateValue);
        LOGGER.info("Enter the date: {}", dateValue);
    }
}
