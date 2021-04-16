# Inventi Automation Framework
## General Info
Inventi Automation Framework is an ecosystem of libraries that allows you to test various kinds and parts of applications. The framework's main features are following:

- Testing of front-end of web applications
- Testing of back-end of web applications
- Multi-language support

### Used Libraries
- TestNG
- Maven
- Jackson
- Lombok
- Log4J
- WebDriverManager
- Selenium
- AspectJ
- Allure (https://docs.qameta.io/allure/)
- REST Assured

## Predispositions
All of these parts need to be installed and correctly set in the system/user environment variables list:
- JDK 15+
- Maven 3.5+
- Allure 2.13+

### Installation
Download resources and run following command in the project root folder:

```
mvn clean install
```

##Global Configuration

###WebDriver Configuration

###Application Under the Test Configuration

###Allure Reports Configuration
Create your own Allure report templates and put them in the **src/main/resources/tpl** folder.

##Running Tests
To launch tests from a particular module:

```
# XML suite:
mvn --projects SOME_MODULE_NAME clean test -DsuiteXmlFile="someSuite.xml" -DsomeParameterName="parameterValue"
# Clear maven:
mvn --projects SOME_MODULE_NAME clean test -Dtest=SomeTest -DsomeParameterName="parameterValue"
# To launch maven test from core-automation-framework module:
mvn --projects core-automation-framework clean test -Dtest=WebElementTests
# To launch core-automation-framewor unit tests suite:
mvn --projects core-automation-framework clean test -DsuiteXmlFile="unitTests.xml"
```

###Generate Allure Test Report
All tests' results are being recorded for Allure report. To display results properly on a local web server, type command:

```
allure serve
```