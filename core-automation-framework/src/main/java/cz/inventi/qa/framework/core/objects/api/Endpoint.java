package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.AuthParameters;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.utils.Utils;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class Endpoint<T> extends ApiObject {

    public Endpoint(AOProps props) {
        super(props);
    }

    public String getFullUrlWithParams() {
        return getProps().getFullUrlWithParams();
    }

    public boolean isParameter() {
        return getProps().isParameter();
    }

    public String getEndpointUrl() {
        return getProps().getEndpointUrl();
    }

    public String getFullUrl() {
        return getProps().getFullUrl();
    }

    /**
     * Returns the closest API authentication parameters either from
     * the endpoint itself, parent endpoint or global level if set.
     * @return AuthParameters object or null
     */
    public AuthParameters getAuthParameters() {
        AuthParameters endpointAuthParameters = getProps().getAuthParameters();
        AuthParameters globalAuthParameters = getProps().getGlobalAuthParameters();
        AuthParameters parentAuthParameters = getParentAuthParameters();
        if (endpointAuthParameters != null) {
            return endpointAuthParameters;
        } else if (parentAuthParameters != null) {
            return parentAuthParameters;
        } else {
            return globalAuthParameters;
        }
    }

    /**
     * Retrieves authentication parameters from the closest
     * parent endpoint (when set).
     * @return AuthParameters object or null
     */
    public AuthParameters getParentAuthParameters() {
        AOProps parentProps = getProps().getParentProps();
        while (parentProps != null) {
            AuthParameters parentAuthParameters = parentProps.getAuthParameters();
            if (parentAuthParameters != null) return parentAuthParameters;
            parentProps = parentProps.getParentProps();
        }
        return null;
    }

    /**
     * Shorthand call to set up AuthParameters of endpoint.
     * @param authParameters AuthParameters object
     */
    public void setEndpointAuthParameters(AuthParameters authParameters) {
        getProps().setAuthParameters(authParameters);
    }

    /**
     * Retrieves AuthParameters for given endpoint.
     * @return endpoint AuthParameters
     */
    public AuthParameters getEndpointAuthParameters() {
        return getProps().getAuthParameters();
    }

    /**
     * Removes endpoint's AuthParameters.
     */
    public void removeEndpointAuthParameters() {
        getProps().setAuthParameters(null);
    }

    /**
     * Sets URL parameter for given endpoint instance.
     * @param urlParameter URL parameter value
     * @return Endpoint object
     */
    @SuppressWarnings("unchecked")
    public T setUrlParameter(String urlParameter) {
        if (urlParameter != null) urlParameter = URLEncoder.encode(urlParameter, StandardCharsets.UTF_8);
        getProps().setParameterValue(urlParameter);
        return (T) this;
    }

    public String getBaseUrl() {
        return getProps().getAppUrl();
    }

    /**
     * Changes the environment outside the TestNG XML suite. TestNG
     * XML suite parameters will be overridden by the new value.
     * @param environmentName environment name
     */
    public void changeEnvironment(String environmentName) {
        TestSuiteParameters.changeParameter(
                "environment",
                environmentName,
                getProps().getAppInstance().getApplicationName()
        );
    }

    /**
     * Adds filter to Rest Assured calls.
     * @param filter filter instance
     * @param <T> type of filter instance
     */
    public <T extends Filter> void addRestAssuredFilter(T filter) {
        RequestSpecification requestSpec = getRestAssuredManager()
                .getRequestSpecification()
                .filter(filter);
        getRestAssuredManager().setRequestSpecification(requestSpec);
    }

    /**
     * Removes filter from Rest Assured calls.
     * @param filter filter class
     * @param <T> type of filter instance
     */
    public <T extends Filter> void removeRestAssuredFilter(Class<T> filter) {
        RequestSpecification requestSpec = getRestAssuredManager()
                .getRequestSpecification()
                .noFiltersOfType(filter);
        getRestAssuredManager().setRequestSpecification(requestSpec);
    }

    public ApiAppInstance<?> getAppInstance() {
        return getProps().getAppInstance();
    }

    public void setAuthToken(String authToken) {
        Log.debug("Setting API level access token for application '"
                + getAppInstance().getApplicationName() + "'"
                + " executed by '" + Utils.getTestIdentifier() + "'"
        );
        getAppInstance()
                .getTestVariablesManager()
                .getApiAppVariables()
                .getAuthParameters()
                .setAuthToken(authToken);
    }

    /**
     * Shorthand call for RestAssuredManager.
     * @return RestAssuredManager
     */
    public RestAssuredManager<?> getRestAssuredManager() {
        return getAppInstance().getRestAssuredManager();
    }

    public String getUrlParameter() {
        return getProps().getParameterValue();
    }

    public abstract Object callGet();

    public abstract Object callGet(Map<String, Object> queryParams);

    public abstract Object callPost(String body);

    public abstract Object callPost();

    public abstract Object callPut(String body);

    public abstract Object callPut();

    public abstract Object callHead();

    public abstract Object callPatch(String body);

    public abstract Object callPatch();

    public abstract Object callDelete();

    public abstract Object callOptions();

    public abstract Object callTrace();

    public abstract Object callConnect();

    public abstract Object createRequest();

    public abstract Object createRequestWithAuth();

    public abstract Object createRequestWithAuthToken(String authToken);

    public abstract Object createRequestWithHttpAuth(String username, String password);

}