package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.variables.api.ApiAppVariables;
import cz.inventi.qa.framework.core.utils.Utils;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;

public class Api extends ApiObject {
    private ApiAppVariables apiAppVariables;

    public Api(AOProps props) {
        super(props);
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
}
