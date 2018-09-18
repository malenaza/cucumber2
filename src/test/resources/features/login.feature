@Login
Feature: Login in page

  Background: Open the browser and navigating to site
    Given I have browser window open
    When I navigate to login page http://the-internet.herokuapp.com/login

  Scenario: Login with right user and password Happy Path
    And login with the following credentials
      | username   |    tomsmith        |
      | password   |SuperSecretPassword!|
    Then I am logged in succesfully
      |message| You logged into a secure area!|

  Scenario: Login with wrong user and right password
    And login with the following credentials
      | username |       tomsmith1    |
      | password |SuperSecretPassword!|
    Then I am not logged in
      |message| Your username is invalid!|

  Scenario: Login with right user and wrong password
    And login with the following credentials
      | username |  tomsmith    |
      | password |SuperPassword |
    Then I am not logged in
      |message| Your password is invalid!|
