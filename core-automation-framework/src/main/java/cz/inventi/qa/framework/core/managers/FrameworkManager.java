package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.Map;
import java.util.Random;

public class FrameworkManager {
    private static FrameworkManager frameworkManager = null;
    private Map<String, AppInstance> appInstances;

    public static FrameworkManager getInstance() {
        if (frameworkManager == null) frameworkManager = new FrameworkManager();
        return frameworkManager;
    }

    public <T extends WebPage> T initWebAppInstance(String browser, String environment, String language, Class<T> startingWebPage, String customAppsConfigPath, String customWebDriverConfigPath) {
        return createNewAppInstance(startingWebPage, customAppsConfigPath, customWebDriverConfigPath).initWebApp(browser, environment, language, startingWebPage);
    }

    public <T extends Api> T initApiAppInstance(String environment, Class<T> api, String customAppsConfigPath, String customWebDriverConfigPath) {
        return createNewAppInstance(api, customAppsConfigPath, customWebDriverConfigPath).initApiApp(environment, api);
    }

    public Map<String, AppInstance> getAppInstances() {
        return appInstances;
    }

    private AppInstance createNewAppInstance(Class<?> startingAppClass, String customAppsConfigPath, String customWebDriverConfigPath) {
        AppInstance appInstance = new AppInstance();

        if (customAppsConfigPath != null && !"".equals(customAppsConfigPath)) {
            appInstance.getConfigManager().setCustomAppConfigPath(customAppsConfigPath);
        }

        if (customWebDriverConfigPath != null && !"".equals(customWebDriverConfigPath)) {
            appInstance.getConfigManager().setCustomWebDriverConfigPath(customWebDriverConfigPath);
        }

        appInstances.put(startingAppClass.getName() + "-" + new Random().nextInt(1000), appInstance);
        return appInstance;
    }

    private AppInstance createNewAppInstance(Class<?> startingAppClass) {
        return createNewAppInstance(startingAppClass,null, null);
    }
}
