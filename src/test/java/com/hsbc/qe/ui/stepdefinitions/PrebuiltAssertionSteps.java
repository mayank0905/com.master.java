package com.hsbc.qe.ui.stepdefinitions;

import com.hsbc.qe.ui.config.Configuration;
import com.hsbc.qe.ui.config.ConfigurationManager;
import com.hsbc.qe.ui.context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PrebuiltAssertionSteps extends BaseSetup {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrebuiltAssertionSteps.class);
    public static Configuration configuration = ConfigurationManager.getConfiguration();
    TestContext testContext;

    public PrebuiltAssertionSteps(TestContext context) {
        super(context);
        this.testContext = context;
    }

    /**
     * Step to check that the given text exactly matches the element text
     */
    @Then("I verify that the {string} text exactly matches the text of the {string} field on the {string} page")
    public void verifyTheElementText(String elementName, String expectedValue, String pageClassName) {
        String actualValue = driverManagerUtils.getElementText(driver, loadWebElement(elementName,pageClassName));
        assertThat("Element text not matching", actualValue, is(equalTo(expectedValue)));
    }

    /**
     * Step to check that the given text partially matches the element text
     */
    @Then("I verify that the {string} text partially matches the text of the {string} field on the {string} page")
    public void verifyTheElementTextPartially(String elementName, String expectedValue, String pageClassName) {
        String actualValue = driverManagerUtils.getElementText(driver, loadWebElement(elementName,pageClassName));
        assertThat("Element text not matching", actualValue, containsString(expectedValue));
    }

    /**
     * Step to check that the given text exactly matches the element text using data table
     */
    @Then("I verify that the following message should exactly match the text of the {string} field on the {string} page")
    public void verifyTheElementTextWithListDataTable(String elementName, String pageClassName, DataTable dataTable) {
        List<List<String>> data = dataTable.asLists();
        String errorMessage = data.get(1).get(0);
        String actualValue = driverManagerUtils.getElementText(driver, loadWebElement(elementName,pageClassName));
        assertThat("Element text not matching", actualValue, is(equalTo(errorMessage)));
    }

    /**
     * Step to check that the given text partially matches the element text with data table
     */
    @Then("I verify that the following message should be included in the text of the {string} field on the {string} page")
    public void verifyTheElementTextPartiallyWithListDataTable(String elementName, String pageClassName, DataTable dataTable) {
        List<List<String>> data = dataTable.asLists();
        String errorMessage = data.get(1).get(0);
        String actualValue = driverManagerUtils.getElementText(driver, loadWebElement(elementName,pageClassName));
        assertThat("Element text not matching", actualValue, containsString(errorMessage));
    }

    @Then("I verify that the {string} element should be displayed on the {string} page")
    public void verifyElementDisplayed(String elementName, String pageClassName) {
        boolean isElementDisplayed = driverManagerUtils.isElementDisplayed(loadWebElement(elementName, pageClassName));
        assertThat("Element should be displayed",  true, is(isElementDisplayed));
    }

    @Then("I verify that the {string} element should not be displayed on the {string} page")
    public void verifyElementNotDisplayed(String elementName, String pageClassName) {
        boolean isElementDisplayed = driverManagerUtils.isElementDisplayed(loadWebElement(elementName, pageClassName));
        assertThat("Element should not be displayed",  false, is(isElementDisplayed));
    }

    /**
     * Step to check that the given text exactly matches the page title
     */
    @Then("I verify that the {string} text exactly matches the current page title")
    public void verifyTheTextMatchesThePageTitle(String ePageTitleText) {
        String aPageTitle = driverManagerUtils.getPageTitle();
        assertThat("Page tiles are not matching", aPageTitle, is(equalTo(ePageTitleText)));
    }

    /**
     * Step to check that the given text partially matches the page title
     */
    @Then("I verify that the {string} text included in the title of the {string} page")
    public void verifyTheTextPartiallyMatchesThePageTitle(String ePageTitleText) {
        String aPageTitle = driverManagerUtils.getPageTitle();
        assertThat("Page tiles are not matching", aPageTitle, containsString(ePageTitleText));
    }

    /**
     * Step to check that the given text exactly matches the page title using data table
     */
    @Then("I verify that the following message should exactly match the page title")
    public void verifyTheTextMatchesThePageTitleDataTable(DataTable dataTable) {
        List<List<String>> data = dataTable.asLists();
        String ePageTitleText = data.get(1).get(0);
        String aPageTitle = driverManagerUtils.getPageTitle();
        assertThat("Page tiles are not matching", aPageTitle, is(equalTo(ePageTitleText)));
    }

    /**
     * Step to check that the given text partially matches the page title with data table
     */
    @Then("I verify that the following message should partially match the page title")
    public void verifyTheTextPartiallyMatchesThePageTitleDataTable(DataTable dataTable) {
        List<List<String>> data = dataTable.asLists();
        String ePageTitleText = data.get(1).get(0);
        String aPageTitle = driverManagerUtils.getPageTitle();
        assertThat("Page tiles are not matching", aPageTitle, containsString(ePageTitleText));
    }

    /**
     * Step to check that the element should be enabled on the page
     */
    @Then("I verify that the {string} element should be enabled on the {string} page")
    public void verifyElementEnabled(String elementName, String elementNotEnabled, String pageClassName)  {
        boolean isElementDisplayed = driverManagerUtils.isElementEnabled(loadWebElement(elementName, pageClassName));
        assertThat("Element should be enabled on the page", isElementDisplayed, equalTo(true));
    }

    /**
     * Step to check that the element should be disabled on the page
     */
    @Then("I verify that the {string} element should be disabled on the {string} page")
    public void verifyElementDisabled(String elementName, String elementNotEnabled, String pageClassName)  {
        boolean isElementDisplayed = driverManagerUtils.isElementEnabled(loadWebElement(elementName, pageClassName));
        assertThat("Element should not be enabled on the page", isElementDisplayed, equalTo(false));
    }

    /**
     * Step to check that the element should be checked or not on the page
     */
    @Then("I verify that the {string} checkbox is checked|unchecked on the {string} page")
    public void i_verify_that_the_checkbox_is_checked_unchecked_on_the_page(String elementName, String elementState, String pageClassName) {
        boolean isElementChecked = driverManagerUtils.isElementSelected(loadWebElement(elementName, pageClassName));
        if(elementState.equals("checked")){
            assertThat("Element should not be unchecked",  true, is(isElementChecked));
        }
        if(elementState.equals("unchecked")){
            assertThat("Element should not be checked",  false, is(isElementChecked));
        }
    }

    /**
     * Step to check that the element should be checked or not on the page
     */
    @Then("I should see alert text as \"(.*?)\"")
    public void check_alert_text(String eAlertMessage)
    {
        String aAlertMessage = driverManagerUtils.getAlertText();
        assertThat("Alert messages are not matching",  aAlertMessage, is(eAlertMessage));
    }
}
