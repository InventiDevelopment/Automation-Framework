# Inventi Automation Framework
### Predispositions
- JDK 8+
- Maven 3.5+

### Installation
Download resources and run following command in the project root folder:

```
mvn clean install
```

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

### Used Libraries
- TestNG
- Maven
- Jackson
- Lombok
- Log4J
- WebDriverManager
- Selenium