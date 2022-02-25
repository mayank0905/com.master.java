package com.hsbc.qe.ui.testrunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
@CucumberOptions
        (plugin = {"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm", "pretty", "json:target/cucumber-report/report.json"},
                monochrome = true,
                features = "src/test/resources/ui/features",
                glue = {"com.hsbc.qe.ui.stepdefinitions"},
                tags = "@Regression")

public class RunUITest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
