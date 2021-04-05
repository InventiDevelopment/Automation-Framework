package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.data.enums.ApiProtocolType;

public class Api extends ApiObject {
    private String baseUrl;
    private ApiProtocolType apiProtocolType;

    public Api(AOProps props) {
        super(props);
    }

    public Api(AOProps props, String baseUrl, ApiProtocolType apiProtocolType) {
        super(props);
        this.baseUrl = baseUrl;
        this.apiProtocolType = apiProtocolType;
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
}
