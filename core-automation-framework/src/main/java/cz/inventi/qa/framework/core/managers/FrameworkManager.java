package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FrameworkManager {
    private static FrameworkManager frameworkManager = null;
    private static RunMode runMode;
    private Map<String, AppInstance> appInstances;

    public FrameworkManager () {
        Log.info("Loading Inventi Automation Framework");
        this.appInstances = new HashMap<>();
        setRunMode();
    }

    private static void setRunMode() {
        String runModeParam = System.getProperty("runMode");

        if (runModeParam == null) {
            runMode = RunMode.NORMAL;
        } else {
            try {
                runMode = RunMode.valueOf(runModeParam.toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("Given runMode parameter '" + runModeParam.toUpperCase() + "' is not supported. Supported values are: '" + Arrays.asList(RunMode.values()) + "'");
            }
        }
        Log.info("Setting framework's run mode to '" + runMode + "'");
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

        Log.info("Creating new application instance of '" + startingAppClass + "'");

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

    public static RunMode getRunMode() {
        return runMode;
    }
}
