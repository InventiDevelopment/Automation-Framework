package cz.inventi.qa.framework.core.objects.variables.api;

import cz.inventi.qa.framework.core.data.enums.api.ApiMandatoryParameters;
import cz.inventi.qa.framework.core.objects.parameters.AuthParameters;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

/**
 * Class to store parameters designed solely for API tests runs.
 * Also provides calls to TestSuiteParameters class.
 */
public class ApiAppVariables {
    private final AuthParameters authParameters;

    public ApiAppVariables() {
        this.authParameters = new AuthParameters();
    }

    public AuthParameters getAuthParameters() {
        return authParameters;
    }

    public String getEnvironment() {
        return TestSuiteParameters.getParameter(ApiMandatoryParameters.ENVIRONMENT.name());
    }
}
