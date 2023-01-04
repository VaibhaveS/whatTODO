Feature: Get feature

  blah blah blah

  Scenario Outline: blah
    Given I populate tasks
    When I fire a GET request to fetch all tasks
    Then I should get a response with HTTP status code <status>
    Examples:
      | status |
      |  200   |


  Scenario Outline: blah
    Given I populate tasks
    When I fire a GET request to fetch all tasks of a particular user <userId>
    Then I should get a response with HTTP status code <status>
    Examples:
    | userId  | status |
    |   1     |  200   |





