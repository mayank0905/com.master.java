package com.hsbc.qe.ui.pageobjects;

import com.hsbc.qe.ui.context.TestContext;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static com.hsbc.qe.ui.config.ConfigurationManager.getConfiguration;
import static org.openqa.selenium.support.PageFactory.initElements;

public class AbstractPageObject {

    TestContext testContext;

    protected AbstractPageObject(TestContext testContext) {
        this.testContext = testContext;
        initElements(new AjaxElementLocatorFactory(testContext.getDriverManager().getDriver(), getConfiguration().timeout()), this);
    }

    public AbstractPageObject() {
    }
}
