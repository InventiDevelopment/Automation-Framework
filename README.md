# Inventi Automation Framework
Boilerplate for easier testing with Java.

## General Info
Inventi Automation Framework is an ecosystem of libraries and design patterns that allows you to test various kinds and parts of applications with ease. The framework's main features are following:

- Testing of front-end of web applications
- Testing of back-end applications (REST)
- Multi-language support
- Nicer reporting with Allure reports

### Used Libraries
This project is a work using multiple libraries. For more details about particular 3rd party software used please visit appropriate POM files in the project. Below we provide basic information about software used along with developer's webpage and license type under which given software is supplied.

- **Allure** (https://docs.qameta.io/allure, Apache License 2.0)
- **Apache Log4J** (https://logging.apache.org/log4j/2.x, Apache License 2.0)
- **Apache Commons** (https://commons.apache.org, Apache License 2.0)
- **Google Guava** (https://github.com/google/guava, Apache License 2.0)
- **Jackson** (https://github.com/FasterXML/jackson, Apache License 2.0)
- **Lombok** (https://projectlombok.org, MIT License)
- **Maven Failsafe Plugin** (https://maven.apache.org/surefire/maven-failsafe-plugin, Apache License 2.0)
- **Maven SureFire Plugin** (http://maven.apache.org/surefire/maven-surefire-plugin, Apache License 2.0)
- **Selenium WebDriver** (https://www.selenium.dev, Apache License 2.0)
- **REST Assured** (https://rest-assured.io, Apache License 2.0)
- **Slf4j** (http://www.slf4j.org, MIT License)
- **TestNG** (https://testng.org/doc, Apache License 2.0)
- **WebDriverManager** (https://github.com/bonigarcia/webdrivermanager, Apache License 2.0)

### Framework License
The Inventi Automation Framework solution is distributed under LGPL-3.0 license. See attached **COPYRIGHT** and **LICENSE** files for more information.

## Predispositions
All of these parts need to be installed and correctly set in the system/user environment variables list:
- **JDK SE** 15+ (https://www.oracle.com/java/technologies/javase-downloads.html)
- **Maven** 3.5+ (https://maven.apache.org/download.cgi)

### Installation
Download resources and run following command in the project root folder:

```
mvn clean install
```

All of the necessary Maven packages will be downloaded (beware if you use custom Maven settings). Installation will run unit tests for the framework by default. If you want to skip these unit tests, add `-DskipTests` parameter to the above mentioned command.

## Global Configuration
Before you begin a development to test your own application, it is necessary to set basic key configuration values in given framework's config files.

### WebDriver Configuration

### Application Under the Test Configuration

### Allure Reports Configuration
Create your own Allure report templates and put them in the **src/main/resources/tpl** folder.

## Running Tests

From an XML suite:
```
mvn --projects SOME_MODULE_NAME clean test -DsuiteXmlFile="someSuite.xml" -DsomeParameterName="parameterValue"
```

By maven:
```
mvn --projects SOME_MODULE_NAME clean test -Dtest=SomeTest -DsomeParameterName="parameterValue"
```

Launch maven test from core-automation-framework module:
```
mvn --projects core-automation-framework clean test -Dtest=WebElementTests
```

Launch core-automation-framework unit tests suite:
```
mvn --projects core-automation-framework clean test -DsuiteXmlFile="unitTests.xml"
```

### Generate Allure Test Report
All tests' results are being recorded for Allure report. To display results properly on a local web server, type command:

```
allure serve
```