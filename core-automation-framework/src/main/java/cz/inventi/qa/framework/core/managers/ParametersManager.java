package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.objects.parameters.ApiAppParameters;
import cz.inventi.qa.framework.core.objects.parameters.CommonParameters;
import cz.inventi.qa.framework.core.objects.parameters.WebAppParameters;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.web.WebPage;

public class ParametersManager {
    private static ApiAppParameters<?> apiAppParameters;
    private static WebAppParameters<?> webAppParameters;
    private static CommonParameters commonParameters;
    private static ApplicationType applicationType;

    public static <T extends Api> void setApiParameters(String environment, Class<T> api) {
        setCommonParameters(environment);
        apiAppParameters = new ApiAppParameters<>(environment, api);
        applicationType = ApplicationType.API;
    }

    public static <T extends WebPage> void setWebParameters(String environment, String browser, String language, Class<T> startingWebpage) {
        setCommonParameters(environment, language);
        webAppParameters = new WebAppParameters<>(environment, browser, language, startingWebpage);
        applicationType = ApplicationType.WEB;
    }

    public static void setCommonParameters(String environment, String language) {
        commonParameters = new CommonParameters(environment, language);
    }

    public static void setCommonParameters(String environment) {
        commonParameters = new CommonParameters("EN", environment);
    }

    public static <T extends Api> ApiAppParameters<T> getApiAppParameters() {
        return (ApiAppParameters<T>) apiAppParameters;
    }

    public static <T extends WebPage> WebAppParameters<T> getWebAppParameters() {
        return (WebAppParameters<T>) webAppParameters;
    }

    public static CommonParameters getCommonParameters() {
        return commonParameters;
    }

    public static ApplicationType getApplicationType() {
        return applicationType;
    }
}
