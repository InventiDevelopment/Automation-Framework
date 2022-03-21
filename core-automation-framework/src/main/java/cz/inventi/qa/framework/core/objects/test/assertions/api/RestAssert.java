package cz.inventi.qa.framework.core.objects.test.assertions.api;

import cz.inventi.qa.framework.core.data.enums.test.AssertionType;
import io.restassured.response.Response;

import java.util.regex.Pattern;

/**
 * Assertion methods for Rest Assured API calls.
 */
public class RestAssert extends RestAssertion {
    private static final AssertionType ASSERTION_TYPE = AssertionType.HARD;

    public static Response assertStatusPassed(Response response) {
        return assertStatusPassed(response, ASSERTION_TYPE);
    }

    public static Response assertStatusFailed(Response response) {
        return assertStatusFailed(response, ASSERTION_TYPE);
    }

    public static Response assertStatusRegex(Response response, String statusPattern) {
        return assertStatusRegex(response, Pattern.compile(statusPattern), ASSERTION_TYPE);
    }
}