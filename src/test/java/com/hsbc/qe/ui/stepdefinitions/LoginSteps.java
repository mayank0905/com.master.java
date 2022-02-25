package com.hsbc.qe.ui.stepdefinitions;

import com.hsbc.qe.api.constants.FilePaths;
import com.hsbc.qe.common.utils.ExcelReader;
import com.hsbc.qe.ui.context.TestContext;
import com.hsbc.qe.ui.pageobjects.LoginPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LoginSteps extends BaseSetup {

    TestContext testContext;
    WebDriver driver;
    LoginPage loginPage;

    public LoginSteps(TestContext testContext){
        super(testContext);
        this.testContext = testContext;
        this.driver = testContext.getDriverManager().getDriver();
        this.loginPage = new LoginPage(this.driver);
    }

    @When("user fills the form from the excel file {string} sheet name {string} row number {int}")
    public void enterLoginFromExcel(String fileName, String sheetName, Integer rowNumber) throws IOException, InvalidFormatException {
        ExcelReader reader = new ExcelReader();
        List<Map<String,String>> testData =
                reader.getDataFromExcelSheet(FilePaths.EXCEL_TEST_DATA + fileName + ".xlsx", sheetName);
        String username = testData.get(rowNumber).get("username");
        String password = testData.get(rowNumber).get("password");
        loginPage.enterLoginDetails(username, password);
    }

    @When("I enter the login details")
    public void entersInvalidCredentials(DataTable userTable) throws IOException, InvalidFormatException {

        List<Map<String, String>> user = userTable.asMaps(String.class, String.class);
        for (Map<String, String> form : user) {
            String locatorName = form.get("LocatorName");
            String pageName = form.get("PageName");
            String excelFileName = form.get("ExcelFileName");
            String sheetName = form.get("SheetName");
            int rowNumber = Integer.parseInt(form.get("RowNumber"));

            ExcelReader reader = new ExcelReader();
            List<Map<String,String>> testData =
                    reader.getDataFromExcelSheet(FilePaths.EXCEL_TEST_DATA + excelFileName + ".xlsx", sheetName);

            if(locatorName.contains("username")) {
                String username = testData.get(rowNumber).get("username");
                WebElement userNameElement = loadWebElement(locatorName, pageName);
                driverManagerUtils.enterText(driver, userNameElement, username);
            }

            if(locatorName.contains("password")){
                String password = testData.get(rowNumber).get("password");
                WebElement passwordElement = loadWebElement(locatorName, pageName);
                driverManagerUtils.enterText(driver, passwordElement, password);
            }
        }
    }
}
