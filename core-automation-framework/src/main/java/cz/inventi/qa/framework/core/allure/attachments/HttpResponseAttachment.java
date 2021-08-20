package cz.inventi.qa.framework.core.allure.attachments;

import cz.inventi.qa.framework.core.allure.AllureRestAssured;
import io.qameta.allure.attachment.AttachmentData;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;

import java.util.List;

/**
 * Custom attachment for Allure's HTTP response.
 * @see io.qameta.allure.attachment.http.HttpResponseAttachment
 */
public class HttpResponseAttachment implements AttachmentData {
    private final String name;
    private final String body;
    private final int responseCode;
    private final List<String> headers;

    public HttpResponseAttachment(Response response) {
        this.name = response.getStatusLine();
        this.responseCode = response.getStatusCode();
        this.headers = AllureRestAssured.toListConverter(response.getHeaders());
        if (response.getBody() != null) {
            body = new Prettifier().getPrettifiedBodyIfPossible(response, response.getBody());
        } else {
            body = null;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<String> getHeaders() {
        return headers;
    }
}
