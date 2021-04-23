# Inventi Automation Framework
Boilerplate for easier testing with Java.

## General Info
Inventi Automation Framework is an ecosystem of libraries and design patterns that allows you to test various kinds and parts of applications with ease. The framework's main features are following:

- Testing of front-end of web applications
  - Using XPATH locators to locate elements
  - Easy to understand web page object structure following the Page-Object patternzzzzz
- Testing of back-end applications (REST)
  - Easy to understand API object structure
- Multi-language support
- Nicer reporting with Allure reports

### Used Libraries
This project is a work using multiple libraries. For more details about particular 3rd party software used please visit appropriate POM files in the project. Below we provide basic information about software used along with developer's webpage and license type under which given software is supplied.

- **Allure** (https://docs.qameta.io/allure, Apache License 2.0)
- **Apache Log4J** (https://logging.apache.org/log4j/2.x, Apache License 2.0)
- **Apache Commons** (https://commons.apache.org, Apache License 2.0)
- **Jackson** (https://github.com/FasterXML/jackson, Apache License 2.0)
- **Lombok** (https://projectlombok.org, MIT License)
- **Maven Failsafe Plugin** (https://maven.apache.org/surefire/maven-failsafe-plugin, Apache License 2.0)
- **Maven SureFire Plugin** (http://maven.apache.org/surefire/maven-surefire-plugin, Apache License 2.0)
- **REST Assured** (https://rest-assured.io, Apache License 2.0)
- **Selenium WebDriver** (https://www.selenium.dev, Apache License 2.0)
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
Download resources and change the Maven project name appropriately to your project (not mandatory). Be sure to check all the corresponding POM files to correctly set up the project parent/child structure.
Later on you can try to run following command in the project root folder:

```
mvn clean install
```

All of the necessary Maven packages will be downloaded (beware if you use custom Maven settings). Installation will run unit tests for the framework by default. If you want to skip these unit tests, add `-DskipTests` parameter to the above mentioned command.

Don't forget to have a peek at the `.gitignore` file to fit your needs and contain your module's folder!

## Global Configuration
Before you begin a development to test your own application, it is necessary to set basic key configuration values in given framework's config files. By default, framework consumes following configuration files:

- Applications configuration YAML file
- WebDriver configuration YAML file

All of the configuration files are loaded from module's **main** `resources/config` folder. By default, `appsConfig.yml` and `webDriverConfig.yml` from `core-automation-framework` module are used. You can provide your own configuration files by putting them in your module's `resources/config` folder and using them with the `@ConfigFiles` annotation in the root test class.

### Multi-Language Setup
If you want to test in multiple languages, you should provide a `language` parameter to the Maven when running tests. Along with parameter there is a need to set up YAML dictionaries for given languages to the **main** `resources/lang` folder (samples can be seen in `core-automation-framework`'s `resources/lang` folder). Dictionary file names have to be corresponding to given language by the ISO 639-1.

### Allure Reports Configuration
You can create your own Allure report templates and put them in the `src/main/resources/tpl` folder. More information can be found in Allure's documentation (link above).

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