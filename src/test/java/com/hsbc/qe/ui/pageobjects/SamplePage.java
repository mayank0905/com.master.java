package com.hsbc.qe.ui.pageobjects;

import com.hsbc.qe.ui.context.TestContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SamplePage extends AbstractPageObject {

    protected SamplePage(TestContext testContext) {
        super(testContext);
    }

    @FindBy(id = "datePickerMonthYearInput")
    public static WebElement datePickerInput;
}
