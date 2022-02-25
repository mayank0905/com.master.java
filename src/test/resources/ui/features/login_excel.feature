Feature:Validate user login functionality with the data driven from excel

  @Regression
  Scenario: Login to the application using excel data
    Given I am in App main site
    When user fills the form from the excel file "testdata" sheet name "contactus" row number 0
    When I click the "loginButton" on the "LoginPage" page
    Then I verify that the "Swag" text exactly matches the current page title

  @Regression
  Scenario: Login to the application using excel data with data table
    Given I am in App main site
    When I enter the login details
      | LocatorName   | PageName  | ExcelFileName | SheetName | RowNumber |
      | usernameInput | LoginPage | testdata      | contactus | 0         |
      | passwordInput | LoginPage | testdata      | contactus | 0         |
    When I click the "loginButton" on the "LoginPage" page
    Then I verify that the "Swag Labs" text exactly matches the current page title
