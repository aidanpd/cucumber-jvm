Feature: Account Withdrawals

  Scenario: Account is in credit
    Given An account 1234 with a balance of 200
    When I withdraw 100 from account 1234
    Then Account 1234 has balance 100
    And A 200 status is returned

  Scenario: Insufficient funds
    Given An account 1234 with a balance of 200
    When I withdraw 250 from account 1234
    Then A 403 status is returned
