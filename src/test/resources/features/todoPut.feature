Feature: Test PUT

  all PUT operations

  Scenario Outline: update a task
    Given I populate tasks
    When I fire a PUT request to update a task with id to toggle task status <taskId>
    Then The given task with <taskId> must have updated status
    Examples:
      | taskId  |
      |   1     |


  Scenario Outline: assign a task to a user
    Given I populate tasks
    When I fire a PUT request to update a task with id <taskId> to assign to user with id <userId>
    Then The given task with <taskId> must be assigned to user with <userId>

    Examples:
      | taskId  | userId |
      |   1     |  200   |
