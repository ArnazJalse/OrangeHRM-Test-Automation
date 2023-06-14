
# OrangeHRM Test Automation

This repository contains automated test scripts for the OrangeHRM application. The tests cover the login functionality, adding employees, updating employee details, and deleting employees. The tests are written using TestNG and utilize Selenium WebDriver for browser automation.

## Prerequisites

Before running the tests, ensure that you have the following software installed:

- Java Development Kit (JDK)
- TestNG
- Selenium WebDriver
- WebDriverManager
- ChromeDriver
- GeckoDriver (for Firefox)
- SafariDriver (for Safari)

## Test Configuration

1. Clone this repository to your local machine.
2. Ensure that you have the necessary dependencies installed as mentioned in the Prerequisites section.
3. Update the paths to the appropriate locations of the drivers (chromedriver, geckodriver, safaridriver) in the `setUp()` method of the `OrangeHRMLogin` class.
4. Update the `testng.xml` file to include the correct browser names and their corresponding values for the `browser` parameter.
5. Run the `testng.xml` file to execute the test cases.

## Test Reports

- The test reports are generated using the ExtentReports library.
- Two separate reports are generated: "AllTests.html" containing all the test cases and "FailedTests.html" containing only the failed test cases.
- The reports include detailed information about the test execution, including logs, browser information, system information, and author information.
- After running the tests, you can open the generated HTML reports to view the results.

## testng.xml

The `testng.xml` file is used to configure the test suite and specify the browsers for parallel execution. It includes three test configurations for Chrome, Firefox, and Safari browsers. You can update the browser names and add/remove configurations as needed.

## Customizing Test Data

If you wish to customize the test data, such as usernames, passwords, employee details, etc., you can modify the corresponding sections in the test script `OrangeHRMLogin`. You can also utilize external data sources like Excel or CSV files to provide test data dynamically.

## Test Execution Modes

The test script `OrangeHRMLogin` is designed to run in two different modes:
- **Sequential Mode:** By default, all test methods are executed sequentially, i.e., one after the other.
- **Parallel Mode:** By enabling the parallel execution mode in the `testng.xml` file, the test methods will be executed concurrently, allowing faster execution.


