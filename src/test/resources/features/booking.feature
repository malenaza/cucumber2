@Booking
Feature: Handle Bookings

  Scenario: Create, Get and Delete booking
    Given an existing booking id
      |firstname      |maria     |
      |lastname       |garcia    |
      |totalprice     |200       |
      |depositpaid    |true      |
      |checkin        |2018-10-01|
      |checkout       |2018-10-07|
      |additionalneeds|Breakfast |
    When I get the details of booking id
    And the details are correct
      |responseCode   |200|
    And I authenticate to get a token
      |username|   admin   |
      |password|password123|
    When I delete the booking id with token
    Then the id no longer exists
      |responseCode   |201|
