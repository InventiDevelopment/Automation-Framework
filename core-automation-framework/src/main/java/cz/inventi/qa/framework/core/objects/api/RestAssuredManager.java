package cz.inventi.qa.framework.core.objects.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestAssuredManager {
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    public RestAssuredManager() {
        requestSpecification = new RequestSpecBuilder().build();
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
}