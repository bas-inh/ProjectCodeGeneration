Feature: User

  #getAllUsers
  Scenario: Get request to /users will result in List<User> of length 1
    Given I have a valid JWT
    When I call the users endpoint
    Then I receive a status of 200
    And Get a List<User> of length 1

  #getAllUsersWithoutAccount
  Scenario: Get request to /users/getAllWithoutAccount will result in List<User> of length 1
    Given I have a valid JWT
    When I call the users endpoint
    Then I receive a status of 200
    And Get a List<User> of length 1

  #addUser
  Scenario: Post request to /users will result in non-null UserDTO object
    Given I have a valid JWT
    When I call the users endpoint
    Then I receive a status of 201
    And Get a non-null UserDTO object

  #updateUser
  Scenario: Put request to /users/getByUsername/{username} will result in non-null UserDTO object
    Given I have a valid JWT
    When I call the users endpoint
    Then I receive a status of 201
    And Get a non-null UserDTO object

  #getUserByUsername
  Scenario: Get request to /users/getByUsername/{username} will result in non-null UserDTO object
    Given I have a valid JWT
    When I call the users endpoint
    Then I receive a status of 201
    And Get a non-null UserDTO object

  #getUserByEmail
  Scenario: Get request to /users/getByEmail/{email} will result in non-null UserDTO object
    Given I have a valid JWT
    When I call the users endpoint
    Then I receive a status of 201
    And Get a non-null UserDTO object

