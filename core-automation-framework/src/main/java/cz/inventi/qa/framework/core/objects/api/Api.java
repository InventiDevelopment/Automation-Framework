package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.framework.AppInstance;

public class Api extends ApiObject {
    private String baseUrl;

    public Api(AOProps props) {
        super(props);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public AppInstance getAppInstance() {
        return getProps().getAppInstance();
    }

    public void setAuthToken(String authToken) {
        getAppInstance()
                .getTestVariablesManager()
                .getApiAppVariables()
                .getAuthParameters()
                .setAuthToken(authToken);
    }
}
