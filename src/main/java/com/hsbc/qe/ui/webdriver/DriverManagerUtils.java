package com.hsbc.qe.ui.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverManagerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);
    private static final long TIME_OUT_IN_SECONDS = 30;
    public static Map<String, String> HandleMyWindows = new HashMap<>();
    public static final int EXPLICIT_TIMEOUT = 5;
    WebDriver driver;

    public DriverManagerUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo(String url) {
        LOGGER.info("CALLED: navigateTo(\"" + url + "\")");
        driver.get(url);
        verifyPageLoaded();
    }

    public void navigateBack() {
        LOGGER.info("CALLED: navigateBack()");
        driver.navigate().back();
        verifyPageLoaded();
    }

    public void navigateForward() {
        LOGGER.info("CALLED: navigateForward()");
        driver.navigate().forward();
        verifyPageLoaded();
    }

    public void refreshPage() {
        LOGGER.info("CALLED: navigateForward()");
        driver.navigate().refresh();
        verifyPageLoaded();
    }

    // HANDLING WINDOWS
    public void switchToNewWindow() {
        System.out.println(driver.getWindowHandles());
        for(String winHandle : driver.getWindowHandles()){
            LOGGER.info("Switching to new windows");
            driver.switchTo().window(winHandle);
        }
    }

    public void closeWindowByName(String windowTitle) {
        LOGGER.info("Switching to the windows by title: " + windowTitle);
        driver.switchTo().window(windowTitle);
        LOGGER.info("Closing windows: "+ windowTitle);
        driver.close();
    }


    public void windowHandle(String WindowsName){
        if (this.HandleMyWindows.containsKey(WindowsName)) {
            driver.switchTo().window(this.HandleMyWindows.get(WindowsName));
            LOGGER.info(String.format("I go to Windows: %s with value: %s ", WindowsName ,this.HandleMyWindows.get(WindowsName)));
        } else {
            for(String winHandle : driver.getWindowHandles()){
                this.HandleMyWindows.put(WindowsName,winHandle);
                System.out.println("The New window"+ WindowsName + "is saved in scenario with value" + this.HandleMyWindows.get(WindowsName));
                LOGGER.info("The New window"+ WindowsName + "is saved in scenario with value" + this.HandleMyWindows.get(WindowsName));
                driver.switchTo().window(winHandle);
            }

        }
    }

    public void zoomTillElementDisplay(WebElement webElement)
    {
        webElement.sendKeys(Keys.chord(Keys.CONTROL, "0"));
    }

    public void scrollPage(String to) throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        if(to.equals("top")){
            LOGGER.info("Scrolling to the top of the page");
            jse.executeScript("scroll(0, -250);");

        }
        else if(to.equals("end")){
            LOGGER.info("Scrolling to the end of the page");
            jse.executeScript("scroll(0, 250);");
        }
    }

    public void verifyPageLoaded (){
        String GetActual = driver.getCurrentUrl();
        LOGGER.info(String.format("Checking if %s page is loaded.", GetActual));
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void openNewTabWithURL(String urlToOpen) {
        LOGGER.info("Open New tab with URL: " + urlToOpen);
        WebDriver newTab = driver.switchTo().newWindow(WindowType.TAB);
        newTab.get(urlToOpen);
    }

    public void scrollToElement(WebElement webElement) throws Exception
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        LOGGER.info("Scrolling to element: " + webElement.getTagName());
        jse.executeScript("arguments[0].scrollIntoView();", webElement);
    }

    public void switchToFrame(WebElement webElement) {
        LOGGER.info("Switching to frame: " + webElement.getTagName());
        driver.switchTo().frame(webElement);
    }

    public void switchToParentFrame() {
        LOGGER.info("Switching to parent frame");
        driver.switchTo().parentFrame();
    }

    public void switchToDefaultContent() {
        LOGGER.info("Switching to default content");
        driver.switchTo().parentFrame();
    }

    public void clickJSElement(WebElement webElement)
    {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        LOGGER.info("Scrolling to element: " + webElement.getTagName());
        jse.executeScript("arguments[0].click()", webElement);
        LOGGER.info("Click the web element");
    }

    public void doubleClickTheElement(WebElement webElement)
    {
        Actions action = new Actions(driver);
        action.moveToElement(webElement).doubleClick().perform();
        LOGGER.info("Double click on element: " + webElement.getTagName());
    }


    public void waitForElementPresent(WebElement elementLocator) throws Exception
    {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        LOGGER.info("Waiting for the element: " + elementLocator.getTagName() + " to be present");
        w.until(ExpectedConditions.presenceOfElementLocated((By) elementLocator));
    }

    public void waitForElementVisible(WebElement elementLocator) throws Exception
    {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        LOGGER.info("Waiting for the element: "+ elementLocator.getTagName() + " to be visible");
        w.until(ExpectedConditions.visibilityOfElementLocated((By) elementLocator));
    }

    /** Method to get page title
     * @return String
     * */
    public String getPageTitle()
    {
        return driver.getTitle();
    }

    /**
     * Function to find element and highlight
     *
     * @param driver Web driver object
     * @param elem   web element to identify
     * @param color  highlighting element
     */

    public void findElementAndHighlight(WebDriver driver, WebElement elem, String color) {
        try {
            explicitlyWaitForElement(driver, elem);
            if (driver instanceof JavascriptExecutor) {
                LOGGER.info("Highlighting: " + elem);
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid " + color + "'",
                        elem);

            }
        } catch (NoSuchElementException e) {
            LOGGER.error("Element highlight method could not find the locator: " + elem);
        } catch (Exception e) {
            LOGGER.error("Some error occurred while highlighting the locator: " + elem);
        }

    }

    /**
     * Function to wait for presence of the element
     *
     * @param driver  Web driver object
     * @param locator web element to identify
     * @return
     */
    public WebElement explicitlyWaitForElement(WebDriver driver, WebElement locator) {
        WebElement foundLocator = null;
        try {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));
            wait.ignoring(StaleElementReferenceException.class);
            foundLocator = wait.until(ExpectedConditions.visibilityOf(locator));
        } catch (TimeoutException e) {
            handleException(e, locator);
        }
        return foundLocator;
    }

    public String getAlertText()
    {
        String alertMessage = null;
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alertMessage = alert.getText();
            LOGGER.info("The alert was accepted successfully.");
        }catch(Throwable e){
            LOGGER.error("Error came while waiting for the alert popup. "+e.getMessage());
        }
        return alertMessage;
    }

    public void acceptTheAlert()
    {
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            LOGGER.info("The alert was accepted successfully.");
        }catch(Throwable e){
            LOGGER.error("Error came while waiting for the alert popup. "+e.getMessage());
        }
    }

    public void dismissTheAlert()
    {
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            LOGGER.info("The alert was dismissed successfully.");
        }catch(Throwable e){
            LOGGER.error("Error came while waiting for the alert popup. "+e.getMessage());
        }
    }

    /**
     * Checks if checkbox is checked.
     * @param webElement locator of the web element
     * @return Returns true if the checkbox is checked.
     */
    public boolean isElementSelected(WebElement webElement) {
        boolean isSelected = webElement.isSelected();
        LOGGER.info("Checked: " + isSelected);
        return isSelected;
    }

    /**
     * Deselects a element only if its selected.
     * @param webElement locator of the web element
     */
    public static void unselectCheckbox(WebElement webElement) {
        LOGGER.info("CALLED: clickCheckbox()");
        if (webElement.isSelected()) {
            webElement.click();
        }
    }

    /**
     * Function to wait for frame and switch to it
     *
     * @param driver  Web driver object
     * @param locator web element to identify
     */
    public void explicitlyWaitForFrame(WebDriver driver, WebElement locator) {
        try {
            WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, TIME_OUT_IN_SECONDS);
            wait.ignoring(StaleElementReferenceException.class);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
        } catch (TimeoutException e) {
            handleException(e, locator);
        }
    }

    /**
     * Navigate to specific url
     *
     * @param url Url to navigate
     */
    public void navigateToURL(String url) {
        driver.get(url);
    }

    /**
     * Function to handle exception
     *
     * @param e       exception
     * @param locator web element to identify
     */
    private void handleException(TimeoutException e, WebElement locator) {
        throw new RuntimeException(" " + locator.toString() + " " + TIME_OUT_IN_SECONDS + " " + driver.getCurrentUrl());
    }


    /**
     * Function to validate web page tile
     *
     * @param expected Expected Title of web page
     */
    public void validateTitle(String expected) {
        String actualText = driver.getTitle();
        Assert.assertEquals(actualText, expected);
    }

    /**
     * Function to click the element
     *
     * @param driver Webdriver object
     * @param ele    web element to identify
     */
    public void clickElement(WebDriver driver, WebElement ele) {
        try {
            findElementAndHighlight(driver, ele, "red");
            ele.click();
        } catch (NoSuchElementException e) {
            handleException(e);
        } catch (StaleElementReferenceException e) {
            findElementAndHighlight(driver, ele, "red");
            ele.click();
        }
    }

    protected void handleException(NoSuchElementException e) {
        Assert.fail(e.getMessage() + " on page " + driver.getCurrentUrl());
    }


    /**
     * function to wait for element to be clickable
     *
     * @param driver  Webdriver object
     * @param webElement By object to identify
     * @return returns element
     */
    public WebElement explicitlyWaitForClickable(WebDriver driver, WebElement webElement) {
        WebElement foundLocator;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));
        wait.ignoring(StaleElementReferenceException.class);
        foundLocator = wait.until(ExpectedConditions.elementToBeClickable(webElement));
        return foundLocator;
    }

    /**
     * Method to hover on element
     *
     * @param driver
     *            : String : Locator type (id, name, class, xpath, css)
     * @param webElement
     *            : String : Locator value
     */
    public void hoverOverElement(WebDriver driver, WebElement webElement) {
        Actions action = new Actions(driver);
        action.moveToElement(webElement).perform();
    }

    /**
     * Function to switch to default content/window
     */
    public void switchToDefaultWindow() {driver.switchTo().defaultContent();
    }


    /**
     * Function to type value
     *
     * @param driver Webdriver object
     * @param ele    By object to identify
     * @param value  Input to be entered
     */
    public void enterText(WebDriver driver, WebElement ele, String value) {
        try {
            findElementAndHighlight(driver, ele, "red");
            ele.click();
            ele.clear();
            ele.sendKeys(value);
        } catch (NoSuchElementException e) {
            handleException(e);
        }
    }

    /**
     * Function to clear the value from the text box
     *
     * @param driver Webdriver object
     * @param ele    By object to identify
     */
    public void clearText(WebDriver driver, WebElement ele) {
        try {
            findElementAndHighlight(driver, ele, "red");
            ele.click();
            ele.clear();
        } catch (NoSuchElementException e) {
            handleException(e);
        }
    }

    public String getElementText(WebDriver driver, WebElement ele) {
        String elementText = null;
        try {
            findElementAndHighlight(driver, ele, "red");
           elementText =  ele.getText();
        } catch (NoSuchElementException e) {
            handleException(e);
        }
        return elementText;
    }
    
    /**
     * @param: String locator. This method verifies if the element is displayed.
     */
    public boolean isElementDisplayed(WebElement element) {
        boolean isDisplayed;
        try {
            LOGGER.info(String.format("Waiting Element: %s", element.getTagName()));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated((By) element)).isDisplayed();
        }catch (NoSuchElementException | TimeoutException e){
            isDisplayed = false;
            LOGGER.info("Element {} not found", element.getTagName());
        }
        LOGGER.info(String.format("%s visibility is: %s", element, isDisplayed));
        return isDisplayed;
    }

    /** Method to return element status - enabled?
     * @param webElement : Web element to check
     * @return Boolean
     */
    public boolean isElementEnabled(WebElement webElement)
    {
        isElementDisplayed(webElement);
        return webElement.isEnabled();
    }

    public void selectOptionDropdownByText(WebElement element, String option) throws Exception
    {
        LOGGER.info(String.format("Waiting Element: %s", element));

        Select opt = new Select(element);
        LOGGER.info("Select option: " + option + "by text");
        opt.selectByVisibleText(option);
    }

    public void selectOptionFromDropdown(WebElement elementToSelect, String optionBy, String option)
    {
        LOGGER.info(String.format("Waiting Element: %s", elementToSelect.getTagName()));

        Select selectList = new Select(elementToSelect);

        switch (optionBy) {
            case "selectByIndex":
                selectList.selectByIndex(Integer.parseInt(option) - 1);
                break;
            case "selectByValue":
                selectList.selectByValue(option);
                break;
            case "selectByText":
                selectList.selectByVisibleText(option);
                break;
        }
    }

    public void waitForTheCurrentPageToLoad (){
        String getCurrentPage = driver.getCurrentUrl();
        LOGGER.info(String.format("Checking if %s page is loaded.", getCurrentPage));
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Function to replace a string
     *
     * @param regex       regular expression to replace
     * @param actualValue value to be replaced
     * @return updated string
     */
    public String getStringFormat(String regex, String actualValue) {
        return String.format(regex, actualValue);
    }

    /**
     * Function to dynamically generate the webelement by substituting strings
     *
     * @param driver            Webdriver object
     * @param locator           Webelement
     * @param runtimeValue      value to be passed during the run time
     * @param substitutionValue value to be substituted
     * @return
     */
    public WebElement prepareWebElementWithDynamicValue(WebDriver driver, String locator, String runtimeValue,
                                                        String substitutionValue) {
        WebDriverWait wait = new WebDriverWait(driver, TIME_OUT_IN_SECONDS);
        WebElement we = null;
        try {
            if (locator.equalsIgnoreCase("id")) {

                we = driver.findElement(By.id(getStringFormat(runtimeValue, substitutionValue)));

            } else if (locator.equalsIgnoreCase("class")) {
                we = driver.findElement(By.className(getStringFormat(runtimeValue, substitutionValue)));
            } else if (locator.equalsIgnoreCase("name")) {
                we = driver.findElement(By.name(getStringFormat(runtimeValue, substitutionValue)));
            } else if (locator.equalsIgnoreCase("css")) {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(getStringFormat(runtimeValue, substitutionValue))));
                we = driver.findElement(By.cssSelector(getStringFormat(runtimeValue, substitutionValue)));
            } else if (locator.equalsIgnoreCase("xpath")) {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getStringFormat(runtimeValue, substitutionValue))));
                we = driver.findElement(By.xpath(getStringFormat(runtimeValue, substitutionValue)));
            } else if (locator.equalsIgnoreCase("linktext")) {
                we = driver.findElement(By.linkText(getStringFormat(runtimeValue, substitutionValue)));
            } else {
                return null;
            }

        } catch (NoSuchElementException ignore) {
            handleException(ignore);
        }
        return we;
    }
}
