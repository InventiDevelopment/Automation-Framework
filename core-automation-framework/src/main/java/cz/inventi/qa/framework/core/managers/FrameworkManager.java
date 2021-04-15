package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FrameworkManager {
    private static FrameworkManager frameworkManager = null;
    private Map<String, AppInstance> appInstances;

    public FrameworkManager () {
        this.appInstances = new HashMap<>();
    }

    public static FrameworkManager getInstance() {
        if (frameworkManager == null) frameworkManager = new FrameworkManager();
        return frameworkManager;
    }

    public <T extends WebPage> T initWebAppInstance(String browser, String environment, String language, Class<T> startingWebPage, ConfigFiles configFiles) {
        return createNewAppInstance(startingWebPage, configFiles).initWebApp(browser, environment, language, startingWebPage);
    }

    public <T extends Api> T initApiAppInstance(String environment, Class<T> api, ConfigFiles configFiles) {
        return createNewAppInstance(api, configFiles).initApiApp(environment, api);
    }

    public Map<String, AppInstance> getAppInstances() {
        return appInstances;
    }

    private AppInstance createNewAppInstance(Class<?> startingAppClass, ConfigFiles configFiles) {
        AppInstance appInstance = new AppInstance();

        if (configFiles != null && !"".equals(configFiles.appsConfig())) {
            appInstance.getConfigManager().setCustomAppConfigPath(configFiles.appsConfig());
        }

        if (configFiles != null && !"".equals(configFiles.driverConfig())) {
            appInstance.getConfigManager().setCustomWebDriverConfigPath(configFiles.driverConfig());
        }

        appInstances.put(startingAppClass.getName() + "-" + new Random().nextInt(1000), appInstance);
        return appInstance;
    }

    private AppInstance createNewAppInstance(Class<?> startingAppClass) {
        return createNewAppInstance(startingAppClass,null);
    }
}
