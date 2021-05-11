package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.parameters.AuthParameters;

import java.util.*;
import java.util.regex.Pattern;

public class AOProps {
    private final AppInstance appInstance;
    private String endpointUrl = "";
    private String fullUrl = "";
    private Object returnKlass;
    private AOProps parentProps;
    private boolean parameter;
    private String parameterValue;
    private ApiAuthMethod apiAuthMethod;

    public AOProps(String url, AppInstance appInstance) {
        this.endpointUrl = url;
        this.appInstance = appInstance;
    }

    public AOProps(String url, Object returnKlass, AOProps parentProps, AppInstance appInstance, ApiAuthMethod apiAuthMethod) {
        this(url, appInstance);
        this.returnKlass = returnKlass;
        this.parentProps = parentProps;
        this.apiAuthMethod = apiAuthMethod;
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

        if (pattern.matcher(url).find()) {
            this.parameter = true;
        } else {
            this.parameter = false;
        }
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

    public String getFullUrl() {
        return fullUrl;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public AppInstance getAppInstance() {
        return appInstance;
    }

    public AuthParameters getAuthParameters() {
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

    public String getBasePath() {
        return getFullUrl().replace(getAppUrl() + "/", "");
    }

    public String getFullUrlWithParams() {
        String fullUrlWithParams = "";
        AOProps currentProps = this;

        while (currentProps != null) {
            if (currentProps.isParameter()) {
                String parameterValue = currentProps.getParameterValue().toLowerCase();
                if ("".equals(parameterValue)) {
                    Log.warn("Endpoint '" + currentProps.endpointUrl + "' is parametrized, but no parameter value has been supplied");
                }
                fullUrlWithParams = parameterValue + "/" + fullUrlWithParams;
            } else {
                fullUrlWithParams = currentProps.endpointUrl + "/" + fullUrlWithParams;
            }
            currentProps = currentProps.parentProps;
        }
        return fullUrlWithParams;
    }

    public Map<String, Object> getPathParams() {
        Map<String, Object> pathParams = new HashMap<>();
        AOProps currentProps = this;

        while (currentProps != null) {
            if (currentProps.isParameter()) {
                String parameterValue = currentProps.getParameterValue().toLowerCase();

                if ("".equals(parameterValue)) {
                    Log.warn("Endpoint '" + currentProps.endpointUrl + "' is parametrized, but no parameter value has been supplied");
                }

                String strippedEndpointUrl = currentProps.endpointUrl
                        .replace("{", "")
                        .replace("}", "");

                pathParams.put(strippedEndpointUrl, parameterValue);
            }
            currentProps = currentProps.parentProps;
        }
        return pathParams;
    }
}
