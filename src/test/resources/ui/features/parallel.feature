Feature: Tests for the parallel testing

  @Regression @Login
  Scenario: Parallel test 1
    Given I am in App main site
    And I enter the value "standard_user" into the "usernameInput" on the "LoginPage" page
    And I enter the value "secret_sauce" into the "passwordInput" on the "LoginPage" page
    When I click the "loginButton" on the "LoginPage" page
    Then I verify that the "Swag Labs" text exactly matches the current page title

  @Regression @Login
  Scenario: Parallel test 2
    Given I am in App main site
    And I enter the value "locked_out_user" into the "usernameInput" on the "LoginPage" page
    And I enter the value "secret_sauce" into the "passwordInput" on the "LoginPage" page
    When I click the "loginButton" on the "LoginPage" page
    And I verify that the following message should be included in the text of the "loginButtonError" field on the "LoginPage" page
      | errorMessage                          |
      | Sorry, this user has been locked out. |

  @Regression @Login
  Scenario: Parallel test 3
    Given I am in App main site
    And I enter the value "standard_user" into the "usernameInput" on the "LoginPage" page
    And I enter the value "secret_sauce" into the "passwordInput" on the "LoginPage" page
    When I click the "loginButton" on the "LoginPage" page
    Then I verify that the "Swag Labs" text exactly matches the current page title

