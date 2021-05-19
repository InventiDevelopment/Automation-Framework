package cz.inventi.qa.framework.core.objects.test.assertions.api;

import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import io.restassured.response.Response;

import java.util.regex.Pattern;

/**
 * Assertion methods for Rest Assured API calls.
 */
public class RestAssert {

    public static Response assertStatusPassed(Response response) {
        Assert.assertTrue(Pattern.compile("2\\d\\d")
                .matcher(Integer.toString(response.getStatusCode())).matches(), "Status code is 2XX");
        return response;
    }

    public static Response assertStatusFailed(Response response) {
        Assert.assertTrue(Pattern.compile("(4\\d\\d|5\\d\\d)")
                .matcher(Integer.toString(response.getStatusCode())).matches(), "Status code is 4XX or 5XX");
        return response;
    }

    public static Response assertStatusIs(int status, Response response) {
        return response
                .then()
                .assertThat()
                .statusCode(status)
                .extract()
                .response();
    }
}


