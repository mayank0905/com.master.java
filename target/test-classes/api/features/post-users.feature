Feature: Create Users

  @apitest
  Scenario Outline: Create user status codes
    Given I have API "<API>"
    And I set content-type as JSON
    And I set request body for "<RequestBody>"
    When I call method 'POST'
    Then I get the response
    Then I verify response code is "<Status_Code>"
    Examples:
      | API         | RequestBody          | Status_Code|
      | create_user | user_data            |    201     |
      | create_user_success| registerdata         |    200     |
      | create_user_unsuccess| register-unsuccesfull|    400    |



    @apitest
    Scenario Outline: create user element comparison
      Given I have API "<API>"
      And I set content-type as JSON
      And I set request body for "<RequestBody>"
      When I call method 'POST'
      And I verify selected elements "<keysValue>" in response
    Examples:
      | API         | RequestBody |keysValue|
      | create_user | user_data   |CreateUsers|
      |create_user_success|registerdata|RegisterationAPISelectedElementsExpectedData|
    |create_user_unsuccess| register-unsuccesfull|RegisterUnsucessfullAPISelectedElementsExpectedData|


  @apitest
  Scenario Outline:  user Schema comparison
    Given I have API "<API>"
    And I set content-type as JSON
    And I set request body for "<RequestBody>"
    When I call method 'POST'
    And I verify the response schema
Examples:
  | API         | RequestBody |
  | create_user | user_data   |
  |create_user_success|registerdata|
  |create_user_unsuccess |register-unsuccesfull|


  @apitest
  Scenario Outline: users Response comparison
    Given I have API "<API>"
    And I set content-type as JSON
    And I set request body for "<RequestBody>"
    When I call method 'POST'
    And I compare the expected response with the actual response with "<COMPARISON_MODE>"

       Examples:
      | API         | RequestBody |COMPARISON_MODE|
      | create_user | user_data   |LENIENT        |
      |create_user_success|registerdata|LENIENT|
      | create_user_unsuccess| register-unsuccesfull|LENIENT|




