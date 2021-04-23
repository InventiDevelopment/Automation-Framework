package cz.inventi.qa.framework.core.objects.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class RestEndpoint<T> extends Endpoint<T> {

    public RestEndpoint(AOProps props) {
        super(props);
    }

    public Response callGet() {
        return createRequest().get();
    }

    public Response callPost(String body) {
        return createRequest()
                .body(body)
                .post();
    }

    public Response callPut(String body) {
        return createRequest()
                .body(body)
                .put();
    }

    public Response callHead() {
        return createRequest().head();
    }

    public Response callPatch(String body) {
        return createRequest()
                .body(body)
                .patch();
    }

    public Response callDelete() {
        return createRequest().delete();
    }

    public Response callOptions() {
        return createRequest().options();
    }

    public Response callTrace() {
        throw new RuntimeException("TRACE method not supported for REST API calls");
    }

    public Response callConnect() {
        throw new RuntimeException("CONNECT method not supported for REST API calls");
    }

    public RequestSpecification createRequest() {
            return RestAssured
                    .given()
                    .baseUri(getProps().getAppUrl())
                    .basePath(getProps().getBasePath())
                    .pathParams(getProps().getPathParams());
    }
}
