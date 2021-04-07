package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.managers.*;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.web.WebPage;

public abstract class BaseTest {
    private final ConfigFiles configFiles;
    private AppInstance appInstance;

    public BaseTest() {
        configFiles = this.getClass().getSuperclass().getDeclaredAnnotation(ConfigFiles.class);
    }

    public <T extends WebPage> T initWebAppInstance(String browser, String environment, String language, Class<T> startingWebPage) {
        T webPageInitialized = FrameworkManager.getInstance().initWebAppInstance(browser, environment, language, startingWebPage, configFiles.appsConfig(), configFiles.driverConfig());
        appInstance = webPageInitialized.getAppInstance();
        return webPageInitialized;
    }

    public <T extends Api> T initApiAppInstance(String environment, Class<T> api) {
        T apiInitialized = FrameworkManager.getInstance().initApiAppInstance(environment, api, configFiles.appsConfig(), configFiles.driverConfig());
        appInstance = apiInitialized.getAppInstance();
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
