# cucumber2
To run the API test in command line:

mvn clean test -Dcucumber.options="src\test\resources\features --tags @Booking"

To run the UI tests in command line:

mvn clean test -Dcucumber.options="src\test\resources\features --tags @Login"
