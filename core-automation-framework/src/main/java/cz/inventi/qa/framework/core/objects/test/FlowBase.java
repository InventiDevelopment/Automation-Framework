package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.managers.*;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.web.WebPage;

public abstract class FlowBase {
    private static ConfigFiles configFiles;
    private static AppInstance appInstance;

    public FlowBase() {
        configFiles = this.getClass().getSuperclass().getDeclaredAnnotation(ConfigFiles.class);
    }

    public static <T extends WebPage> T getWebAppInstance(Class<T> startingWebPage) {
        T webPageInitialized = FrameworkManager.getInstance().initWebAppInstance(startingWebPage, configFiles);
        appInstance = webPageInitialized.getAppInstance();
        Log.debug(appInstance.getApplicationType() + " app instance of '" + startingWebPage + "' has been successfully initialized");
        return webPageInitialized;
    }

    public static <T extends Api> T getApiAppInstance(Class<T> api) {
        T apiInitialized = FrameworkManager.getInstance().initApiAppInstance(api, configFiles);
        appInstance = apiInitialized.getAppInstance();
        Log.debug(appInstance.getApplicationType() + " app instance of '" + api + "' has been successfully initialized");
        return apiInitialized;
    }

    public ConfigFiles getConfigFiles() {
        return configFiles;
    }

    public AppInstance getAppInstance() {
        return appInstance;
    }

    public ConfigManager getConfigManager() {
        return appInstance.getConfigManager();
    }

    public WebDriverManager getWebDriverManager() {
        return appInstance.getWebDriverManager();
    }

    public AppManager getAppManager() {
        return appInstance.getAppManager();
    }

    public LanguageManager getLanguageManager() {
        return appInstance.getLanguageManager();
    }

    public ParametersManager getParametersManager() {
        return appInstance.getParametersManager();
    }
}
