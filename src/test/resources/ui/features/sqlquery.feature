Feature: SQL database testing

  @sql @WIP
  Scenario: Update the database table
    Given I connect to the database
    When I execute the UPDATE SQL query to update the database table
      | SQLQuery                                                 |
      | UPDATE Accounts SET ACCOUNT_TYPE = 'Standard' WHERE ID=1 |
    Then I execute the SELECT SQL query to check that the table is updated
      | SQLQuery                             | ColumnName  | ExpectedValue |
      | SELECT TOWN FROM Accounts WHERE ID=1 | AccountType | Standard      |
    And I close the database connection
