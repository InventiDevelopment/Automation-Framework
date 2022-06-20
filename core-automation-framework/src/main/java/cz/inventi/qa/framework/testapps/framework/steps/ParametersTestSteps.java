package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.core.objects.test.steps.StepsBase;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import io.qameta.allure.Step;

public class ParametersTestSteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);

    @Step("Retrieve Parameter for Current Application ({paramName})")
    public ParametersTestSteps retrieveCurrentApplicationParameter(
            String paramName,
            String expectedValue
    ) {
        assertParamValue(
                TestSuiteParameters.getParameter(paramName, homePage.getAppInstance().getApplicationName()),
                expectedValue
        );
        return this;
    }

    @Step("Check Test Suite Parameters Are not Null")
    public ParametersTestSteps checkTestSuiteParametersAreNotNull() {
        Assert.assertNotEquals(
                TestSuiteParameters.getParameters().size(),
                0,
                "At least 1 test suite parameter was loaded"
        );
        return this;
    }

    @Step("Retrieve Parameter for Specific Application ({appName}, {paramName})")
    public ParametersTestSteps retrieveSpecificApplicationParameter(
            String appName,
            String paramName,
            String expectedValue
    ) {
        assertParamValue(TestSuiteParameters.getParameter(paramName, appName), expectedValue);
        return this;
    }

    @Step("Retrieve Shared Application Parameter")
    public ParametersTestSteps retrieveSharedApplicationParameter(
            String paramName,
            String expectedValue
    ) {
        assertParamValue(TestSuiteParameters.getParameter(paramName), expectedValue);
        return this;
    }

    private void assertParamValue(String paramValue, String expectedValue) {
        Assert.assertNotNull(
                paramValue,
                "Parameter value is not null"
        );
        Assert.assertEquals(
                paramValue,
                expectedValue,
                "Retrieved correct value (" + expectedValue + ")"
        );
    }
}

