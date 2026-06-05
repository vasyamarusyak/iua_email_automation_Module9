To build test automation framework based on WebDriver + Java + TestNG  task from the [Module 08] WebDriver.
The framework should have:
WebDriverManager for managing drivers for different browsers;
PageObject / PageFactory for abstract pages;
Necessary business model (business objects for dedicated entities);
Property files with test data for different environments (at least 2);
XML suites for Smoke and Regression tests;
Possibility to make a screenshot in case of test failure; The log should have information about the saved screenshot in this case;
Flexibility on different parameters e.g., browser, test suite, environment (this flexibility will help CI integration in future);
Add logging of every step (with log4j or any similar lib) for your solution implemented based on previous modules:

Configure logs format in informative way
Demonstrate usage of different log levels (debug, action, error, etc)
Configure ability to write logs in console and to save logs in a file (a new file should be created for each day). By default logs are written in console and are stored in file.
9. Test results should present on job graphics, and screenshots should be archived as artifacts.


Bonus Task
Implement your test scenario (or part of it, if it is possible) using some ready-made Selenium wrapper/framework (e.g. Selenide, Serenity, JDI, HtmlElements, etc)
Use highlighting of elements during test execution. If any action is performed on any element during test execution, this element should be highlighted.

To build an allure report - pull the project, run any test/suits of tests and use the command "allure serve allure-results"
To run tests with maven - mvn test -Dproperty=qa -Dbrowser=chrome -DsuiteXmlFile="src/test/resources/suites/smoke.xml"
To run on jenkins use command java -jar jenkins.war --httpPort=8081 (This command is used because firefox doesn't want to run when jenkins is lounched in services.msc)