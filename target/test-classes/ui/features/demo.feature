Feature: Create Hotel Bookings
  In order to make bookings
  Users should be able to create new bookings
  @regression
  Scenario: User should be able to login to swaglabs app
    Given user navigated to the "swaglabs" page
    And I enter the username as "standard_user"
    And I enter the password as "secret_sauce"
    When I click the login button
    Then I should navigate to the home page

