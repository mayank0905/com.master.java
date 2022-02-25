package com.hsbc.qe.ui.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPageObject {

    public HomePage() {
    }
    @FindBy(id = "user-name")
    public static WebElement usernameInput;

    public WebElement loginButtonElement;
}