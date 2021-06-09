package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.util.Map;

public abstract class TestBase {
    /**
     * Sets all the parameters supplied through TestNG suite
     * or Maven command to the TestSuiteParameters class.
     * @param context TestNG context
     */
    @BeforeSuite(alwaysRun = true)
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

    /**
     * Quit framework and all related instances after
     * test class run.
     */
    @AfterClass(alwaysRun = true)
    public void quit() {
        FrameworkManager.quit();
    }

    /**
     * Has to be called after the loadTestSuiteParameters()
     * method. Initialize steps needed for your tests here.
     * These steps will have access to the TestNG
     * parameters.
     */
    @BeforeClass
    public abstract void initSteps();
}
