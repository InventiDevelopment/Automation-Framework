package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.api.filters.ProxyFilter;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredManager<T extends Endpoint<?>> {
    private final ApiAppInstance<T> apiAppInstance;
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public RestAssuredManager(ApiAppInstance<T> apiAppInstance) {
        this.apiAppInstance = apiAppInstance;
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
        if (apiAppInstance.getConfigManager().getCurrentApiAppConfig().isRelaxedHttpsValidation()) {
            requestSpecification = requestSpecification.setRelaxedHTTPSValidation();
        }
        return requestSpecification;
    }

    private RequestSpecBuilder setRestAssuredProxy(RequestSpecBuilder requestSpecification) {
        return requestSpecification.addFilter(new ProxyFilter());
    }
}