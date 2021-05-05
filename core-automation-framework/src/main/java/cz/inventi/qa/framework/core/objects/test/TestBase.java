package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlTest;

import java.util.Map;

public abstract class TestBase {
    /**
     * Sets all the parameters supplied through TestNG suite
     * or Maven command to the TestSuiteParameters class.
     * @param context TestNG context
     */
    @BeforeSuite
    public void loadTestSuiteParameters(ITestContext context) {
        XmlTest currentTest = context.getCurrentXmlTest();
        Map<String, String> parameters = currentTest.getAllParameters();

        for (String key : parameters.keySet()) {
            String parameterValue = parameters.get(key);
            if (parameterValue.startsWith("$")) {
                String newValue = currentTest.getParameter(parameterValue.substring(1));
                parameters.put(key, newValue);
            }
        }

        TestSuiteParameters.setParameters(parameters);
    }
}
