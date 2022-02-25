Feature: List Users

  @apitest
  Scenario Outline: Get users Status
    Given I have API "<API>"
    And I set content-type as JSON
    When I call method 'GET'
    Then I get the response
    Then I verify response code is "<Status_Code>"
    Examples:
      | API         |       Status_Code|
      | get_single_user |     200     |
      | get_all_users |        200     |
      | user_not_found|    404    |


  @apitest
  Scenario Outline: get user element comparison
    Given I have API "<API>"
    And I set content-type as JSON
    When I call method 'GET'
    And I verify selected elements "<keysValue>" in response
    Examples:
      | API         |  keysValue|
      | get_single_user |GetSingleUserAPISelectedElementsExpectedData|
      |get_all_users|GetAllUserAPISelectedElementsExpectedData|
     # |user_not_found| ResourceDatanotfound|



  @apitest
  Scenario Outline:Get Schema comparison
    Given I have API "<API>"
    And I set content-type as JSON
    When I call method 'GET'
    And I verify the response schema
    Examples:
      | API         |
      | get_single_user |
      |get_all_users|
      #|user_not_found |


  @apitest
  Scenario Outline:Get Response comparison
    Given I have API "<API>"
    And I set content-type as JSON
    When I call method 'GET'
    And I compare the expected response with the actual response with "<COMPARISON_MODE>"
    Examples:
      | API       | COMPARISON_MODE  |
      | get_single_user |    LENIENT |
      | get_all_users |    LENIENT |
    # | user_not_found |    LENIENT |

