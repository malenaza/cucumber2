@Booking
Feature: Handle Bookings

  Scenario: Create, Get and Delete booking
    Given I get the details of an existing booking id
    And the details are correct
      |responseCode   |200|
    And I authenticate to get a token
      |username|   admin   |
      |password|password123|
    When I delete the booking id with token
    Then the id no longer exists
      |responseCode   |201|
