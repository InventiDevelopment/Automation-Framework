package cz.inventi.qa.framework.core.allure.attachments;

import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.PreemptiveOAuth2HeaderScheme;
import io.restassured.specification.FilterableRequestSpecification;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Helps with parsing of Rest Assured's authentication
 * data so that it can be retrieved for the attachment.
 */
public class AuthenticationSchemeParser {
    private Class<? extends AuthenticationScheme> authenticationSchemeClass;
    private Map<String, String> authenticationSpecs;
    private String authenticationName;

    public AuthenticationSchemeParser(FilterableRequestSpecification requestSpecification) {
        authenticationSpecs = new HashMap<>();
        parseAuthorizationMethod(requestSpecification);
    }

    /**
     * Fills all necessary data for the report into a HashMap.
     * @param requestSpecification Rest Assured's FilterableRequestSpecification
     */
    private void parseAuthorizationMethod(FilterableRequestSpecification requestSpecification) {
        AuthenticationScheme authenticationScheme = requestSpecification.getAuthenticationScheme();
        authenticationSchemeClass = authenticationScheme.getClass();
        authenticationName = authenticationSchemeClass.getSimpleName();
        if (PreemptiveOAuth2HeaderScheme.class.equals(authenticationSchemeClass)) {
            String accessToken = reflectivelyGetFieldValueAsString("accessToken", requestSpecification);
            authenticationSpecs.put("Access Token", accessToken);
        }
    }

    /**
     * Reflectively accesses Rest Assured's AuthenticationScheme class data.
     * @param fieldName name of the field
     * @param requestSpecification FilterableRequestSpecification
     * @return field value in String
     */
    private String reflectivelyGetFieldValueAsString(
            String fieldName,
            FilterableRequestSpecification requestSpecification
    ) {
        try {
            Field field = requestSpecification.getAuthenticationScheme().getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(requestSpecification.getAuthenticationScheme());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new FrameworkException(
                    "Cannot retrieve field '" + fieldName + "' from given AuthenticationScheme",
                    e
            );
        }
    }

    public Map<String, String> getAuthenticationSpecs() {
        return authenticationSpecs;
    }

    public String getAuthenticationName() {
        return authenticationName;
    }
}