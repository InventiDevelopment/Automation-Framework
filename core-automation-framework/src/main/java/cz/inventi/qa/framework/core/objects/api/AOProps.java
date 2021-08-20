package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.AuthParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Api Object Properties file to hold crucial information
 * about given ApiObject and its parents necessary for
 * API object initialization.
 */
public class AOProps {
    private final AppInstance appInstance;
    private final String endpointUrl;
    private String fullUrl = "";
    private Class<?> returnKlass;
    private boolean parameter;
    private String parameterValue;
    private ApiAuthMethod apiAuthMethod;
    private ApiObject parentApiObject;
    private AuthParameters authParameters;

    public AOProps(String url, AppInstance appInstance) {
        this.endpointUrl = url;
        this.appInstance = appInstance;
    }

    public <T extends ApiObject> AOProps(
            String url,
            T parentApiObject,
            AOProps parentProps,
            AppInstance appInstance,
            ApiAuthMethod apiAuthMethod
    ) {
        this(url, appInstance);
        this.apiAuthMethod = apiAuthMethod;
        this.parentApiObject = parentApiObject;
        if (parentApiObject != null) {
            this.returnKlass = parentApiObject.getClass();
        }
        checkIfAOIsParameter(url);
        composeFullUrl(parentProps);
    }

    private void composeFullUrl(AOProps parentProps) {
        fullUrl += endpointUrl;
        if (parentProps != null) {
            fullUrl = parentProps.fullUrl + "/" + fullUrl;
        }
    }

    private boolean checkIfAOIsParameter(String url) {
        Pattern pattern = Pattern.compile("\\{.*}", Pattern.CASE_INSENSITIVE);
        this.parameter = pattern.matcher(url).find();
        return parameter;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public Object getReturnKlass() {
        return returnKlass;
    }

    public ApiAuthMethod getAuthMethod() {
        return apiAuthMethod;
    }

    public boolean isParameter() {
        return parameter;
    }

    public ApiObject getParentApiObject() {
        return parentApiObject;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public AOProps getParentProps() {
        if (parentApiObject != null) {
            return parentApiObject.getProps();
        } else {
            return null;
        }
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public void setAuthParameters(AuthParameters authParameters) {
        this.authParameters = authParameters;
    }

    public AppInstance getAppInstance() {
        return appInstance;
    }

    public AuthParameters getGlobalAuthParameters() {
        return appInstance
                .getTestVariablesManager()
                .getApiAppVariables()
                .getAuthParameters();
    }

    public String getAppUrl() {
        return appInstance
                .getConfigManager()
                .getCurrentApplicationEnvironmentUrl();
    }

    public AuthParameters getAuthParameters() {
        return authParameters;
    }

    public String getBasePath() {
        return getFullUrl().replace(getAppUrl() + "/", "");
    }

    public String getFullUrlWithParams() {
        StringBuilder fullUrlWithParams = new StringBuilder();
        AOProps currentProps = this;

        while (currentProps != null) {
            if (currentProps.isParameter()) {
                String parameterValue = currentProps.getParameterValue().toLowerCase();
                if ("".equals(parameterValue)) {
                    Log.warn(
                            "Endpoint '" + currentProps.endpointUrl + "' is parametrized, " +
                            "but no parameter value has been supplied"
                    );
                }
                fullUrlWithParams.insert(0, parameterValue + "/");
            } else {
                fullUrlWithParams.insert(0, currentProps.endpointUrl + "/");
            }
            currentProps = currentProps.getParentProps();
        }
        return fullUrlWithParams.toString();
    }

    public Map<String, Object> getPathParams() {
        Map<String, Object> pathParams = new HashMap<>();
        AOProps currentProps = this;

        while (currentProps != null) {
            if (currentProps.isParameter()) {
                String parameterValue = currentProps.getParameterValue();

                if ("".equals(parameterValue) || parameterValue == null) {
                    Log.warn(
                            "Endpoint '" + currentProps.endpointUrl + "' is parametrized," +
                                    " but no parameter value has been supplied"
                    );
                } else {
                    parameterValue = parameterValue.toLowerCase();
                }

                String strippedEndpointUrl = currentProps.endpointUrl
                        .replace("{", "")
                        .replace("}", "");

                pathParams.put(strippedEndpointUrl, parameterValue);
            }
            currentProps = currentProps.getParentProps();
        }
        return pathParams;
    }
}
