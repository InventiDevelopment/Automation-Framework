package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrameworkManager {
    private final Map<String, AppInstance> appInstances;
    private static FrameworkManager frameworkManager = null;
    private static RunMode runMode;

    public FrameworkManager() {
        this.appInstances = new HashMap<>();
        Log.info("Loading Inventi Automation Framework");
        setRunMode();
    }

    public <T extends WebPage> T initWebAppAt(Class<T> webPage) {
        return getOrInitializeAppInstance(webPage).initWebPage(webPage);
    }

    public <T extends Api> T initApiAppAt(Class<T> api) {
        return getOrInitializeAppInstance(api).initApi(api);
    }

    public static Map<String, AppInstance> getAppInstances() {
        return getInstance().appInstances;
    }

    public static FrameworkManager getInstance() {
        if (frameworkManager == null) frameworkManager = new FrameworkManager();
        return frameworkManager;
    }

    public static void quitAppInstances() {
        ArrayList<String> appNamesToQuit = new ArrayList<>();
        getAppInstances().forEach((appName, appInstance) -> appNamesToQuit.add(appName));
        appNamesToQuit.forEach(FrameworkManager::quitAppInstance);
    }

    public static void quitAppInstance(String appName) {
        Log.info("Quiting application instance of '" + appName + "'");
        getAppInstance(appName).quit();
        getAppInstances().remove(appName);
    }

    public static void quit() {
        Log.info("Quitting Inventi Automation Framework");
        quitAppInstances();
    }

    public static RunMode getRunMode() {
        return runMode;
    }

    private static void setRunMode() {
        String runModeParam = System.getProperty("runMode");

        if (runModeParam == null) {
            runMode = RunMode.NORMAL;
        } else {
            try {
                runMode = RunMode.valueOf(runModeParam.toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("Given runMode parameter '" + runModeParam.toUpperCase() +
                        "' is not supported. Supported values are: '" + Arrays.asList(RunMode.values()) + "'");
            }
        }
        Log.info("Setting framework's run mode to '" + runMode + "'");
        Log.setLogLevel(runMode);
    }

    private AppInstance getOrInitializeAppInstance(Class<?> appClass) {
        String appName = validateAndGetAppName(appClass);
        AppInstance existingAppInstance = getAppInstance(appName);
        if (existingAppInstance == null) {
            existingAppInstance = new AppInstance(getApplicationType(appClass), appName);
            appInstances.put(appName, existingAppInstance);
            return existingAppInstance;
        } else {
            return existingAppInstance;
        }
    }

    private ApplicationType getApplicationType(Class<?> startingAppClass) {
        if (Api.class.isAssignableFrom(startingAppClass)) {
            return ApplicationType.API;
        }
        if (WebPage.class.isAssignableFrom(startingAppClass)) {
            return ApplicationType.WEB;
        }
        return null;
    }

    private static AppInstance getAppInstance(String appName) {
        return getAppInstances().getOrDefault(appName, null);
    }

    private static String validateAndGetAppName(Class<?> appClass) {
        Class<?> currentClass = appClass;

        while (!currentClass.equals(Object.class)) {
            Application applicationAnnotation = currentClass.getDeclaredAnnotation(Application.class);
            if (applicationAnnotation != null) {
                return applicationAnnotation.name();
            }
            currentClass = currentClass.getSuperclass();
        }

        throw new RuntimeException("@Application(\"YOUR_APP_NAME\") annotation is not set anywhere on supplied" +
                " class '" + appClass.getName() + "' or its ancestors");
    }
}