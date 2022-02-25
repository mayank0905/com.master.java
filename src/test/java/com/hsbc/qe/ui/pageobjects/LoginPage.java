package com.hsbc.qe.ui.pageobjects;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends AbstractPageObject {

    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "user-name")
    public static WebElement usernameInput;

    @FindBy(id = "password")
    public static WebElement passwordInput;

    @FindBy(id = "login-button")
    public static WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    public static WebElement loginButtonError;

    public void enterUserName(String email) {
        this.usernameInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        this.passwordInput.sendKeys(password);
    }

    public void enterLoginDetails(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }
}
