package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.api.filters.ProxyFilter;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredManager {
    private final AppInstance appInstance;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public RestAssuredManager(AppInstance appInstance) {
        this.appInstance = appInstance;
        requestSpecification = prepareRequestSpec().build();
        responseSpecification = new ResponseSpecBuilder().build();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public ResponseSpecification getResponseSpecification() {
        return responseSpecification;
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public void setResponseSpecification(ResponseSpecification responseSpecification) {
        this.responseSpecification = responseSpecification;
    }

    private RequestSpecBuilder prepareRequestSpec() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        setRelaxedHttps(requestSpecBuilder);
        setRestAssuredProxy(requestSpecBuilder);
        return requestSpecBuilder;
    }

    private RequestSpecBuilder setRelaxedHttps(RequestSpecBuilder requestSpecification) {
        if (appInstance.getConfigManager().getCurrentApiAppConfig().isRelaxedHttpsValidation()) {
            requestSpecification = requestSpecification.setRelaxedHTTPSValidation();
        }
        return requestSpecification;
    }

    private RequestSpecBuilder setRestAssuredProxy(RequestSpecBuilder requestSpecification) {
        return requestSpecification.addFilter(new ProxyFilter());
    }
}