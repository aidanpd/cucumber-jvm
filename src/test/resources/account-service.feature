Feature: Account Withdrawals

  Scenario: Account is in credit
    Given Account 1234 with balance 200
    When I withdraw 100 from account 1234
    Then Account 1234 has balance 100

#  Scenario: Insufficient funds
#    Given My account is in credit
#    And My account has a balance of 200
#    When I withdraw 250
#    Then An exception is thrown
