package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;


public abstract class RestEndpoint<T> extends Endpoint<T> {

    public RestEndpoint(AOProps props) {
        super(props);
    }

    @Override
    public Response callGet() {
        return createRequest().get();
    }

    @Override
    public Response callGet(Map<String, Object> queryParams) {
        return createRequest()
                .queryParams(queryParams)
                .get();
    }

    @Override
    public Response callPost(String body) {
        return createRequest()
                .body(body)
                .post();
    }

    @Override
    public Response callPost() {
        return createRequest().post();
    }

    @Override
    public Response callPut(String body) {
        return createRequest()
                .body(body)
                .put();
    }

    @Override
    public Response callPut() {
        return createRequest().put();
    }

    @Override
    public Response callHead() {
        return createRequest().head();
    }

    @Override
    public Response callPatch(String body) {
        return createRequest()
                .body(body)
                .patch();
    }

    @Override
    public Response callPatch() {
        return createRequest().patch();
    }

    @Override
    public Response callDelete() {
        return createRequest().delete();
    }

    @Override
    public Response callOptions() {
        return createRequest().options();
    }

    @Override
    public Response callTrace() {
        throw new FrameworkException("TRACE method not supported for REST API calls");
    }

    @Override
    public Response callConnect() {
        throw new FrameworkException("CONNECT method not supported for REST API calls");
    }

    @Override
    public RequestSpecification createRequest() {
            if (!ApiAuthMethod.NONE.equals(getAuthMethod())) {
                return createRequestWithAuth();
            } else {
                return prepareRequest();
            }
    }

    @Override
    public RequestSpecification createRequestWithAuth() {
        return switch (getAuthMethod()) {
            case OAUTH2 -> createRequestWithAuthToken(getAuthParameters().getAuthToken());
            case HTTPAUTH -> createRequestWithHttpAuth(
                    getAuthParameters().getUsername(),
                    getAuthParameters().getPassword()
            );
            case SAML, OAUTH1, OPENID -> throw new FrameworkException("Auth method currently not supported.");
            default -> prepareRequest();
        };
    }

    @Override
    public RequestSpecification createRequestWithAuthToken(String authToken) {
        return prepareRequest().with().auth().oauth2(authToken);
    }

    @Override
    public RequestSpecification createRequestWithHttpAuth(String username, String password) {
        return prepareRequest().with().auth().basic(username, password);
    }

    /**
     * Prepare RA RequestSpecification with URL, path parameters
     * and other options derived from app specific parameters.
     * @return Rest Assured RequestSpecification
     */
    private RequestSpecification prepareRequest() {
        RequestSpecification appSpecificRequestSpecification = getProps()
                .getAppInstance()
                .getRestAssuredManager()
                .getRequestSpecification();
        RequestSpecification requestSpecification = RestAssured
                .given(appSpecificRequestSpecification)
                .baseUri(getProps().getAppUrl())
                .basePath(getProps().getBasePath())
                .pathParams(getProps().getPathParams());
        return requestSpecification.when();
    }
}