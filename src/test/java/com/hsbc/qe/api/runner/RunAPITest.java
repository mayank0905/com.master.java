package com.hsbc.qe.api.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/api/features"},
        glue = {"com.hsbc.qe.api.stepdefs"},
        tags = "@apitest",
        plugin = {"pretty", "html:target/cucumber/cucumber-report.html", "json:target/cucumber.json",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"
        })
public class RunAPITest {
}
