Feature: Transaction

  Scenario: Creating a new transaction
    Given the user has a valid token for role "user"
    And the user has a valid new transaction
    When the user makes a post request to the transactions endpoint
    Then the system returns a status of 200