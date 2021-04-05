package cz.inventi.qa.framework.core.objects.parameters;

import cz.inventi.qa.framework.core.objects.api.Api;

public class ApiAppParameters <T extends Api> {
    private String environment;
    private Class<T> api;

    public ApiAppParameters(String environment, Class<T> api) {
        this.environment = environment;
        this.api = api;
    }
}
