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
    private final ApiAppVariables apiAppVariables;
    private final WebAppVariables webAppVariables;
    private final Map<String, String> customVariables;
    private final String applicationName;

    public TestVariablesManager(String applicationName) {
        this.applicationName = applicationName;
        apiAppVariables = new ApiAppVariables(applicationName);
        webAppVariables = new WebAppVariables(applicationName);
        customVariables = new HashMap<>();
    }

    public ApiAppVariables getApiAppVariables() {
        return apiAppVariables;
    }

    public WebAppVariables getWebAppVariables() {
        return webAppVariables;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public TestSuiteParameters getTestSuiteParameters() {
        return TestSuiteParameters.getInstance();
    }

    public Map<String, String> getCustomVariables() {
        return customVariables;
    }
}