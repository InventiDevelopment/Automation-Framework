package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.variables.api.ApiAppVariables;
import cz.inventi.qa.framework.core.utils.Utils;

public class Api extends ApiObject {
    private String baseUrl;
    private ApiAppVariables apiAppVariables;

    public Api(AOProps props) {
        super(props);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public AppInstance getAppInstance() {
        return getProps().getAppInstance();
    }

    public void setAuthToken(String authToken) {
        Log.debug("Setting API level access token for application '"
                + getAppInstance().getApplicationName() + "'"
                + " executed by '" + Utils.getCallerTestClassName() + "'"
        );
        getAppInstance()
                .getTestVariablesManager()
                .getApiAppVariables()
                .getAuthParameters()
                .setAuthToken(authToken);
    }
}
