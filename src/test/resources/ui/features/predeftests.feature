Feature: Tests for the predefined step definitions

  @Regression
  Scenario: Select the date
    Given I navigate to "https://demoqa.com/date-picker"
    When I enter the date as today with 5 days into the "datePickerInput" on the "SamplePage" page
    Then I verify that the "jhdf" text exactly matches the text of the "datePickerInput" field on the "SamplePage" page
