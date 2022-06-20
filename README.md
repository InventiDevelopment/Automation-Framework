  # Inventi Automation Framework
Boilerplate for easier testing with Java.

## General Info
**Inventi Automation Framework** is an ecosystem of libraries and design patterns that allows you to test various kinds and parts of applications with ease. The framework's main features are following:

- Testing of front-end of web applications
  - Using **XPATH** locators to locate elements
  - Easy to understand web page object structure following the **Page-Object pattern**
- Testing of back-end applications (**REST**)
  - Easy to understand API object structure
- **Multi-language** support
- Simultaneous testing of different applications
  - Easily implement custom solution for your application type
- Better reporting and structure with **Allure reports**
- **Open-sourced**

### Used Libraries
This project is a Maven configuration using multiple third-party libraries for compiling, testing and in other phases. For more details about particular software used please visit appropriate POM files in the project. Below we provide basic information about software used along with developer's webpage and license type under which given software is supplied.

- **[Allure Framework](https://docs.qameta.io/allure)** (Apache License 2.0)
- **[AssertJ](https://assertj.github.io/doc/)** (Apache License 2.0)
- **[Apache Commons](https://commons.apache.org)** (Apache License 2.0)
- **[Apache Log4J](https://logging.apache.org/log4j/2.x)** (Apache License 2.0)
- **[AspectJ Weaver](https://www.eclipse.org/aspectj)** (Eclipse Public License 1.0)
- **[Google Guava](https://github.com/google/guava)** (Apache License 2.0) 
- **[Hibernate Validator](https://hibernate.org/validator/)** (Apache License 2.0)
- **[Jackson](https://github.com/FasterXML/jackson)** (Apache License 2.0)
- **[Lombok](https://projectlombok.org)** (MIT License)
- **[Maven SureFire Plugin](http://maven.apache.org/surefire/maven-surefire-plugin)** (Apache License 2.0)
- **[REST Assured](https://rest-assured.io)** (Apache License 2.0)
- **[Selenium WebDriver](https://www.selenium.dev)** (Apache License 2.0)
- **[TestNG](https://testng.org/doc)** (Apache License 2.0)
- **[WebDriverManager](https://github.com/bonigarcia/webdrivermanager)** (Apache License 2.0)

### Framework License
The **Inventi Automation Framework** solution (`core-automation-framework` module artifact) is distributed under **Apache License 2.0**. See attached **[NOTICE](NOTICE)** and **[LICENSE](LICENSE)** files located at the `core-automation-framework` folder for more information.

Other artifacts supplied along this project are meant for framework's usage demonstration purposes.

## Predispositions
All of these parts need to be installed and correctly set in the system/user environment variables list:
- **[JDK SE](https://www.oracle.com/java/technologies/javase-downloads.html)** 15+
- **[Maven](https://maven.apache.org/download.cgi)** 3.5+
- **[Allure Framework](https://docs.qameta.io/allure)** 2.13.7+

### Installation
Download resources and change the Maven project name appropriately to your project (not mandatory). Be sure to check all the corresponding POM files to correctly set up the project parent/child structure.
Later on you can try to run following command in the project root folder:

```
mvn clean install -DskipTests
```

All the necessary Maven packages will be downloaded (beware if you use custom Maven settings). Installation will run unit tests for the framework by default. If you want to skip these unit tests, add `-DskipTests` parameter to the above mentioned command.

After the installation is finished, create a Maven module for your application under test.

Don't forget to have a peek at the `.gitignore` file to fit your needs and contain your module's folder!

## Global Configuration
Before you begin the development to test your own application, it is necessary to set basic configuration values supplying framework's config files. By default, framework consumes following configuration files:

- Applications configuration YAML file - mandatory for all application types (`appConfig.yml`)
- WebDriver configuration YAML file - mandatory for web applications (`webDriverConfig.yml`)

All these configuration files are loaded from module's `src/main/resources/<your-application-name>/config` folder. By default, `appConfig.yml` and `webDriverConfig.yml` from `core-automation-framework` module are used. You need to provide at least the `appConfig.yml` into every of your project module's `src/main/<your-application-name>/folder`. You can also provide more of your own configuration files by putting them in the same resources folder and supplying their name in `appconfig` (`-Dappconfig`) and `webdriverconfig` (`-Dwebdriverconfig`) parameters in your TestNG suite (or Maven command).

`<your-application-name>` stands for the name of application under test. It is recommended to use lowercase names without spaces. This identifier should be unique across all Maven modules in the project (to avoid resource file conflicts).

### Multi-Language Setup
If you want to test in multiple languages, you should provide a `language` (`-Dlanguage`) parameter to Maven when running tests. Along with mentioned parameter there is a need to set up YAML dictionaries for given languages to the `src/main/resources/<your-application-name>/lang/<language-iso-identifier>` folder (samples can be seen in `core-automation-framework` module). Dictionary file names have to be corresponding to given language by the ISO 639-1.

### Allure Reports Configuration
You can create your own Allure report templates and put them in the `src/main/resources/tpl` folder. More information can be found in Allure's documentation (link above).

### Test Resources
There is a possibility to use `test-resources` folder in the project root folder to save data needed for your tests in one place. You can adjust path to this folder in the project's global POM file at `test.resources.directory` property. Calling `Utils.getTestResourcesFolder()` will give you Path to the folder.

## Running Tests

### Basic Test Run
Framework uses **TestNG** and **Maven** configuration to run tests. You can run tests as follows:

From an XML suite:
```
mvn --projects <YOUR_PROJECT_MODULE_NAME> clean test -DsuiteXmlFile=<TESTNG-SUITE-FILE> -D<PARAMETER-NAME>=<PARAMETER-VALUE>
```

From a test class directly:
```
mvn --projects <YOUR_PROJECT_MODULE_NAME> clean test -Dtest=<TEST-CLASS-NAME> -D<PARAMETER-NAME>=<PARAMETER-VALUE>
```

Launch maven test from core-automation-framework module:
```
mvn --projects core-automation-framework clean test -Dtest=WebElementTests
```

Launch core-automation-framework unit tests suite:
```
mvn --projects core-automation-framework clean test -DsuiteXmlFile=framework/unitTests.xml
```

### Passing Parameters to the Test
You can pass parameters either by using the TestNG suite xml file, or directly with Maven command (see command sample above).

#### Running Multiple Applications Under Test Simultaneously
If your test uses more applications at once, it is recommended to write input parameters in format of `parameterName:applicationname`. In this way, you can supply parameter with same name for multiple projects with different values for each of the projects at once - supplying application name helps to assign parameter value to a specific application. Given parameter is used for all the applications in case of using multiple applications in one test without supplying application name to the parameter. 

#### Passing Application Dependent Mandatory Parameters Through TestNG Suite
To pass application dependent mandatory parameter (i.e. like **environment**) using the TestNG suite, it is necessary to put this parameter directly under the `<suite>` or `<test>` tag. Other parameters can be passed through deeper levels of TestNG suite.

### Framework Run Mode
You can set up framework run modes (**NORMAL**, **DEBUG**) adding `-DrunMode` Maven parameter to command with selected mode value. Run mode affects mainly log information that is being displayed in the console output. By default, **NORMAL** run mode is applied.

### Allure Test Results
Test results files are saved by default to the directory `test-results/allure-results` in the project's root folder. You can change this folder in the global **[POM configuration file](pom.xml)** of the project.

#### Generate Allure Test Report
All tests' results are being recorded for Allure report. To display results properly on a local web server, type command:

```
allure serve
```

#### Masking sensitive data in reports
There is a possibility to mask sensitive data in Allure's results files and Allure reports - by default all TestNG input parameters' values that have name containing _"password"_ or _"secret"_ are masked with `[**MASKED**]`. Their values will be hidden only if other than `DEBUG` framework run mode is used. This solution should be reworked in the future to use `@Secret` annotation and AspectJ or Allure native solution.

### Parallelization
By default, framework allows parallelization on the level of TestNG's `classes` level and above in the case of using the `TestBase.class` and `StepsBase.class` pattern. It is recommended to define `thread-count` value in TestNG suite to equal number of defined classes in given test. 