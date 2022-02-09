package cz.inventi.qa.framework.core.objects.test.assertions.api;

import cz.inventi.qa.framework.core.data.enums.test.AssertionType;
import cz.inventi.qa.framework.core.objects.framework.FrameworkAssertionException;
import cz.inventi.qa.framework.core.objects.test.assertions.Assertion;
import io.restassured.response.Response;

/**
 * Assertion methods for Rest Assured API calls.
 */
public class RestAssertion extends Assertion {

    public static Response assertStatusPassed(Response response, AssertionType assertionType) {
        return assertStatusRegex(response,"2\\d\\d", assertionType);
    }

    public static Response assertStatusFailed(Response response, AssertionType assertionType) {
        return assertStatusRegex(response,"(4\\d\\d|5\\d\\d)", assertionType);
    }

    public static Response assertStatusRegex(
            Response response,
            String statusPattern,
            AssertionType assertionType
    ) {
        try {
            assertEqualsRegex(
                    statusPattern,
                    Integer.toString(response.getStatusCode()),
                    "Status code conforms regex '" + statusPattern + "'",
                    assertionType
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