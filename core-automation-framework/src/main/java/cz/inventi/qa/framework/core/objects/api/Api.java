package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.data.enums.api.ApiProtocolType;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;

public class Api extends ApiObject {
    private String baseUrl;
    private ApiProtocolType apiProtocolType;

    public Api(AOProps props) {
        super(props);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public ApiProtocolType getApiProtocolType() {
        return apiProtocolType;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setApiProtocolType(ApiProtocolType apiProtocolType) {
        this.apiProtocolType = apiProtocolType;
    }

    public AppInstance getAppInstance() {
        return getProps().getAppInstance();
    }


    public void setAuthToken(String authToken) {
        getAppInstance()
                .getParametersManager()
                .getApiAppParameters()
                .getAuthParameters()
                .setAuthToken(authToken);
    }

}
