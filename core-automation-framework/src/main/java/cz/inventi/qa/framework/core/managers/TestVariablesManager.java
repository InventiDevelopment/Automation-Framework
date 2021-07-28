package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.variables.api.ApiAppVariables;
import cz.inventi.qa.framework.core.objects.variables.web.WebAppVariables;

import java.util.HashMap;
import java.util.Map;

/**
 * Class allowing setting variables bound to current AppInstance.
 */
public class TestVariablesManager {
    private final TestSuiteParameters testSuiteParameters;
    private final ApiAppVariables apiAppVariables;
    private final WebAppVariables webAppVariables;
    private final Map<String, String> customVariables;

    public TestVariablesManager() {
        testSuiteParameters = TestSuiteParameters.getInstance();
        apiAppVariables = new ApiAppVariables();
        webAppVariables = new WebAppVariables();
        customVariables = new HashMap<>();
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

    public Map<String, String> getCustomVariables() {
        return customVariables;
    }
}