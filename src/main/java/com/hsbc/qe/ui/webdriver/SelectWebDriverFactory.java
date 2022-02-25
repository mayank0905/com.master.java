package com.hsbc.qe.ui.webdriver;

import com.hsbc.qe.ui.custom_exceptions.TargetNotValidException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import static com.hsbc.qe.ui.config.ConfigurationManager.getConfiguration;

public class SelectWebDriverFactory {

    private static final Logger LOGGER = LogManager.getLogger(SelectWebDriverFactory.class);

    public WebDriver createInstance(String browser) {
        Target target = Target.valueOf(getConfiguration().target().toUpperCase());
        WebDriver webdriver;

        switch (target) {
            case LOCAL:
                webdriver = WebDriverFactory.valueOf(browser.toUpperCase()).createDriver();
                break;
            case REMOTE:
                webdriver = createRemoteInstance(WebDriverFactory.valueOf(browser.toUpperCase()).getOptions());
                break;
            default:
                throw new TargetNotValidException(target.toString());
        }
        return webdriver;
    }

    private RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            String gridURL = String.format("http://%s:%s", getConfiguration().gridUrl(), getConfiguration().gridUrl());

            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), capability);
        } catch (java.net.MalformedURLException e) {
            LOGGER.error("Grid URL is invalid or Grid is not available");
            LOGGER.error(String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            LOGGER.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }

    enum Target {
        LOCAL, REMOTE
    }

}
