package com.hsbc.qe.ui.reporter;

import com.github.automatedowl.tools.AllureEnvironmentWriter;
import com.google.common.collect.ImmutableMap;
import com.hsbc.qe.ui.context.TestContext;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import static com.hsbc.qe.ui.config.ConfigurationManager.getConfiguration;
import static org.openqa.selenium.OutputType.BYTES;

public class AllureManager {

    TestContext testContext;

    public AllureManager(TestContext context){
        this.testContext = context;
    }

    public static void setAllureEnvironmentInformation() {
        AllureEnvironmentWriter.allureEnvironmentWriter(
                ImmutableMap.<String, String>builder().
                        put("Test URL", getConfiguration().url()).
                        put("Target execution", getConfiguration().target()).
                        put("Global timeout", String.valueOf(getConfiguration().timeout())).
                        put("Headless mode", String.valueOf(getConfiguration().headless())).
                        put("Local browser", getConfiguration().browser()).
                        put("Grid URL", getConfiguration().gridUrl()).
                        put("Grid port", getConfiguration().gridPort()).
                        build());
    }

    @Attachment(value = "Failed test screenshot", type = "image/png")
    public static byte[] takeScreenshotToAttachOnAllureReport(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(BYTES);
    }

    //Text attachments for Allure
    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    //HTML attachments for Allure
    @Attachment(value = "{0}", type = "text/html")
    public static String attachHtml(String html) {
        return html;
    }

    //Text attachments for Allure
    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] saveScreenshotPNG(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}