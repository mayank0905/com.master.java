Feature: Validate user login functionality

  @Regression @Login
  Scenario: As a registered user, user should be able to logged in
    Given I am in App main site
    And I enter the value "standard_user" into the "usernameInput" on the "LoginPage" page
    And I enter the value "secret_sauce" into the "passwordInput" on the "LoginPage" page
    When I click the "loginButton" on the "LoginPage" page
    Then I verify that the "Swag Labs" text exactly matches the current page title

  @Regression @Login
  Scenario: As a locked user, user should not be logged in
    Given I am in App main site
    And I enter the value "locked_out_user" into the "usernameInput" on the "LoginPage" page
    And I enter the value "secret_sauce" into the "passwordInput" on the "LoginPage" page
    When I click the "loginButton" on the "LoginPage" page
    And I verify that the following message should be included in the text of the "loginButtonError" field on the "LoginPage" page
      | errorMessage                          |
      | Sorry, this user has been locked out. |

  @Regression @Login
  Scenario: As an user with invalid credentials, user should not be logged in
    Given I am in App main site
    And I enter the value "standard_user" into the "usernameInput" on the "LoginPage" page
    And I enter the value "secret_sauce" into the "passwordInput" on the "LoginPage" page
    When I click the "loginButton" on the "LoginPage" page
    Then I verify that the "Swag" text exactly matches the current page title

