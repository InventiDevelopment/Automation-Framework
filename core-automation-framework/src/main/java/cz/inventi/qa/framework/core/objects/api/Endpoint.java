package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.Log;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class Endpoint<T> extends ApiObject {

    public Endpoint(AOProps props) {
        super(props);
    }

    public Response callGet() {
        return handleRestRequest(Method.GET);
    }

    public Response callPost() {
        return handleRestRequest(Method.POST);
    }

    public Response callPut() {
        return handleRestRequest(Method.PUT);
    }

    public Response callHead() {
        return handleRestRequest(Method.HEAD);
    }

    public Response callPatch() {
        return handleRestRequest(Method.PATCH);
    }

    public Response callDelete() {
        return handleRestRequest(Method.DELETE);
    }

    public Response callOptions() {
        return handleRestRequest(Method.OPTIONS);
    }

    public Response callTrace() {
        return handleRestRequest(Method.TRACE);
    }

    public Response callConnect() {
        return null;
    }

    public String getFullUrlWithParams () {
        return getProps().getFullUrlWithParams();
    }

    public boolean isParameter() {
        return getProps().isParameter();
    }

    public String getEndpointUrl () {
        return getProps().getEndpointUrl();
    }

    public String getFullUrl() {
        return getProps().getFullUrl();
    }

    public T setUrlParameter(String urlParameter) {
        getProps().setParameterValue(URLEncoder.encode(urlParameter, StandardCharsets.UTF_8));
        return (T) this;
    }

    public String getUrlParameter() {
        return getProps().getParameterValue();
    }

    private Response handleRestRequest(Method method) {
        Log.debug("Building '" + method + "' request at '" + getFullUrlWithParams() + "'");

        try {
            URL url = new URL(getFullUrlWithParams());
            Response response = RestAssured
                    .given()
                    .when()
                    .log()
                    .all()
                    .request(method, url);

            Log.debug("Received response:");
            response.then().log().all();
            return response;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not parse request URL '" + getFullUrlWithParams() + "': " + e);
        }
    }
}
