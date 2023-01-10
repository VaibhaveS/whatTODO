Feature: Batch feature

  All batch operations

  Scenario Outline: fetch all tasks from csv
    Given my csv file named <csvName> exists
    When I fire a PUT request to populate tasks from CSV
    Then I should have all tasks from <csvName> in the repository
    Examples:
      | csvName |
      |  "todos.csv"   |







