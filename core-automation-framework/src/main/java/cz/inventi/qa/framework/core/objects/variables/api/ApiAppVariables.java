package cz.inventi.qa.framework.core.objects.variables.api;

import cz.inventi.qa.framework.core.objects.parameters.AuthParameters;
import cz.inventi.qa.framework.core.objects.variables.AppVariables;

/**
 * Class to store parameters designed solely for API tests runs.
 * Also provides calls to TestSuiteParameters class.
 */
public class ApiAppVariables extends AppVariables {
    private final AuthParameters authParameters;

    public ApiAppVariables(String applicationName) {
        super(applicationName);
        this.authParameters = new AuthParameters();
    }

    public AuthParameters getAuthParameters() {
        return authParameters;
    }
}
