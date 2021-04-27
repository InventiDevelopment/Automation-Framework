package cz.inventi.qa.framework.core.objects.parameters;

import cz.inventi.qa.framework.core.objects.api.Api;

public class ApiAppParameters <T extends Api> {
    private String environment;
    private Class<T> api;
    private AuthParameters authParameters;

    public ApiAppParameters(String environment, Class<T> api) {
        this.environment = environment;
        this.api = api;
        this.authParameters = new AuthParameters();
    }

    public AuthParameters getAuthParameters() {
        return authParameters;
    }
}
