package cz.inventi.qa.framework.core.objects.variables.api;

import cz.inventi.qa.framework.core.objects.parameters.AuthParameters;

/**
 * Class to store parameters designed solely for API endpoint.
 * Also provides calls to TestSuiteParameters class.
 */
public class EndpointVariables {
    private AuthParameters authParameters;

    public EndpointVariables() {
    }

    public EndpointVariables(AuthParameters authParameters) {
        this.authParameters = authParameters;
    }

    public void setAuthParameters(AuthParameters authParameters) {
        this.authParameters = authParameters;
    }

    public AuthParameters getAuthParameters() {
        return authParameters;
    }
}
