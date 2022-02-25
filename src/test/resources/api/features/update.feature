Feature: Update Users

  @apitest  @updateuser
  Scenario Outline: Update User
    Given I have API "<API>"
    And I set content-type as JSON
    And I set request body for "<RequestBody>"
    When I call method 'PUT'
    Then I get the response
    Then I verify response code is 200
    And I verify the response schema
    And I verify selected elements "UpdateUserAPISelectedElementsExpectedData" in response
    And I compare the expected response with the actual response with "<COMPARISON_MODE>"

    Examples:
      | API         | RequestBody |COMPARISON_MODE|
      | update_users | updateUsers  |      LENIENT       |