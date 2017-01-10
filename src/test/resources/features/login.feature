Feature: Search in casasantasusana website
I want go to santasusanastudenthouseinlima and search for estudiantes
 
Scenario: Navigate to santasusanastudenthouseinlima website and perform search
Given I have browser window open
When I Navigate to santasusanastudenthouseinlima
And search for estudiantes
Then I should see the number results page
