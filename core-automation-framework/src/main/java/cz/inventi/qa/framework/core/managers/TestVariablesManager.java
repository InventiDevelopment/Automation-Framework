package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.objects.variables.api.ApiAppVariables;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.variables.web.WebAppVariables;

/**
 * Class allowing setting variables bound to current
 * application run according to application type.
 */
public class TestVariablesManager {
    private final TestSuiteParameters testSuiteParameters;
    private final ApiAppVariables apiAppVariables;
    private final WebAppVariables webAppVariables;

    public TestVariablesManager() {
        testSuiteParameters = TestSuiteParameters.getInstance();
        apiAppVariables = new ApiAppVariables();
        webAppVariables = new WebAppVariables();
    }

    public ApiAppVariables getApiAppVariables() {
        return apiAppVariables;
    }

    public WebAppVariables getWebAppVariables() {
        return webAppVariables;
    }

    public TestSuiteParameters getTestSuiteParameters() {
        return testSuiteParameters;
    }
}