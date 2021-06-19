package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
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
    public Response callPut(String body) {
        return createRequest()
                .body(body)
                .put();
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
            case OAUTH2 -> prepareRequest()
                    .header("Authorization", "Bearer " + getAuthParameters().getAuthToken());
            case SAML, OAUTH1, OPENID, HTTPAUTH -> throw new FrameworkException("Auth method currently not supported.");
            default -> prepareRequest();
        };
    }

    /**
     * Prepare RA RequestSpecification with URL, path parameters
     * and other options.
     * @return Rest Assured RequestSpecification
     */
    private RequestSpecification prepareRequest() {
        /* Prepare request URL and fetch path parameters */
        RequestSpecification requestSpecification = RestAssured
                .given()
                .baseUri(getProps().getAppUrl())
                .basePath(getProps().getBasePath())
                .pathParams(getProps().getPathParams());

        /* Set relaxed HTTPS validation according to API settings */
        if (getProps().getAppInstance().getConfigManager().getCurrentApiAppConfig().isRelaxedHttpsValidation()) {
            requestSpecification = requestSpecification.relaxedHTTPSValidation();
        }

        return requestSpecification.when();
    }
}
