package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.objects.variables.api.ApiAppVariables;
import cz.inventi.qa.framework.core.objects.variables.web.WebAppVariables;

/**
 * Class allowing setting variables bound to current AppInstance.
 */
public class AppVariablesManager extends VariablesManager {
    private final ApiAppVariables apiAppVariables;
    private final WebAppVariables webAppVariables;

    public AppVariablesManager(String applicationName) {
        apiAppVariables = new ApiAppVariables(applicationName);
        webAppVariables = new WebAppVariables(applicationName);
    }

    public ApiAppVariables getApiAppVariables() {
        return apiAppVariables;
    }

    public WebAppVariables getWebAppVariables() {
        return webAppVariables;
    }
}