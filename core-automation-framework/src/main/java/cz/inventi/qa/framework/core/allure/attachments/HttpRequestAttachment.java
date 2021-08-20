package cz.inventi.qa.framework.core.allure.attachments;

import cz.inventi.qa.framework.core.allure.AllureRestAssured;
import io.qameta.allure.attachment.AttachmentData;
import io.restassured.internal.support.Prettifier;
import io.restassured.specification.FilterableRequestSpecification;

import java.util.List;
import java.util.Map;

/**
 * Custom attachment for Allure's HTTP request.
 * @see io.qameta.allure.attachment.http.HttpRequestAttachment
 */
public class HttpRequestAttachment implements AttachmentData {
    private final String url;
    private final int port;
    private final String name;
    private final String method;
    private final String body;
    private final List<String> headers;
    private final Map<String, String> cookies;
    private final Map<String, String> formParams;
    private final Map<String, String> queryParams;
    private AuthenticationSchemeParser authentication;
    private String curl;

    public HttpRequestAttachment(FilterableRequestSpecification requestSpec) {
        method = requestSpec.getMethod();
        url = requestSpec.getURI();
        port = requestSpec.getPort();
        headers = AllureRestAssured.toListConverter(requestSpec.getHeaders());
        formParams = requestSpec.getFormParams();
        queryParams = requestSpec.getQueryParams();
        cookies = AllureRestAssured.toMapConverter(requestSpec.getCookies());
        name = "Request";
        if (requestSpec.getBody() != null) {
            body = new Prettifier().getPrettifiedBodyIfPossible(requestSpec);
        } else {
            body = null;
        }
        authentication = new AuthenticationSchemeParser(requestSpec);
    }

    @Override
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getFormParams() {
        return formParams;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public AuthenticationSchemeParser getAuthentication() {
        return authentication;
    }
}