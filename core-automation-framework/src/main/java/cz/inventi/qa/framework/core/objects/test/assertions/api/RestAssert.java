package cz.inventi.qa.framework.core.objects.test.assertions.api;

import cz.inventi.qa.framework.core.objects.framework.FrameworkAssertionException;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import io.restassured.response.Response;

import java.util.regex.Pattern;

/**
 * Assertion methods for Rest Assured API calls.
 */
public class RestAssert {

    public static Response assertStatusPassed(Response response) {
        return assertStatusRegex(response,"2\\d\\d");
    }

    public static Response assertStatusFailed(Response response) {
        return assertStatusRegex(response,"(4\\d\\d|5\\d\\d)");
    }

    public static Response assertStatusRegex(Response response, String statusPattern) {
        try {
            Assert.assertTrue(Pattern.compile(statusPattern)
                    .matcher(Integer.toString(response.getStatusCode())).matches(),
                    "Status code conforms regex '" + statusPattern + "'"
            );
            return response;
        } catch (FrameworkAssertionException e) {
            throw new FrameworkAssertionException(
                    "Unexpected status returned in response!\n" + response.statusLine() + "\n\n"
                    + "Headers:\n" + response.headers().toString() + "\n\n"
                    + "Body:\n" + response.asPrettyString(),
                    e
            );
        }
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