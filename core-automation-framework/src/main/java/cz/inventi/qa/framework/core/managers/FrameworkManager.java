package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.data.config.ProxySettings;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.data.enums.ProxyScheme;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.framework.core.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrameworkManager {
    private final Map<String, Map<String, AppInstance>> appInstances;
    private volatile static FrameworkManager frameworkManager = null;
    private static RunMode runMode;
    private static ProxySettings proxySettings;

    public FrameworkManager() {
        this.appInstances = new HashMap<>();
        Log.info("Loading Inventi Automation Framework");
        setProxy();
        setRunMode();
    }

    /**
     * Creates proxy settings if supplied in Maven command.
     */
    private void setProxy() {
        String proxyServer = System.getProperty("proxyServer");
        String proxyPort = System.getProperty("proxyPort");

        if (proxyServer != null && proxyPort != null) {
            proxySettings = new ProxySettings(
                    System.getProperty("proxyUser"),
                    System.getProperty("proxyPass"),
                    proxyServer,
                    Integer.parseInt(proxyPort),
                    Utils.getEnum(ProxyScheme.class, System.getProperty("proxyScheme"))
            );
        }
    }

    public <T extends WebPage> T initWebAppAt(Class<T> webPage, String identifier) {
        return retrieveOrInitializeAppInstance(webPage, identifier).initWebPage(webPage);
    }

    public <T extends Api> T initApiAppAt(Class<T> api, String identifier) {
        return retrieveOrInitializeAppInstance(api, identifier).initApi(api);
    }

    public static Map<String, Map<String, AppInstance>> getAppInstances() {
        return getInstance().appInstances;
    }

    synchronized public static FrameworkManager getInstance() {
        if (frameworkManager == null) frameworkManager = new FrameworkManager();
        return frameworkManager;
    }

    public static void quitTestAppInstances(String testClassName) {
        Log.info("Quitting all AppInstances created by test '" + testClassName + "'");
        Map<String, Map<String, AppInstance>> appInstances = getAppInstances();
        appInstances.get(testClassName).forEach((appName, appInstance) -> appInstance.quit());
        appInstances.remove(testClassName);
        if (getAppInstances().size() == 0) Log.info("No AppInstances left running in the stack");
    }

    public static RunMode getRunMode() {
        return runMode;
    }

    private static void setRunMode() {
        String runModeParam = System.getProperty("runMode");
        if (runModeParam == null || "".equals(runModeParam)) {
            runMode = RunMode.NORMAL;
        } else {
            try {
                runMode = RunMode.valueOf(runModeParam.toUpperCase());
            } catch (Exception e) {
                throw new FrameworkException("Given runMode parameter '" + runModeParam.toUpperCase() +
                        "' is not supported. Supported values are: '" + Arrays.asList(RunMode.values()) + "'");
            }
        }
        Log.info("Setting framework's run mode to '" + runMode + "'");
        Log.setGlobalLogLevel(runMode);
    }

    private AppInstance retrieveOrInitializeAppInstance(Class<?> appClass, String testClassName) {
        String appName = validateAndGetAppName(appClass);
        if (appInstances.get(testClassName) == null) appInstances.put(testClassName, Map.of());
        AppInstance appInstance = appInstances.get(testClassName).get(appName);
        if (appInstance == null) {
            Log.info("Creating AppInstance (" + appClass.getName() + ") for test '" + testClassName + "'");
            appInstance = new AppInstance(getApplicationType(appClass), appName);
            appInstances.put(testClassName, Map.of(appName, appInstance));
            Log.info("Successfully created new AppInstance for test (" + appClass.getName() + ")");
        }
        return appInstance;
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

    private static String validateAndGetAppName(Class<?> appClass) {
        Class<?> currentClass = appClass;
        while (!currentClass.equals(Object.class)) {
            Application applicationAnnotation = currentClass.getDeclaredAnnotation(Application.class);
            if (applicationAnnotation != null) {
                return applicationAnnotation.name();
            }
            currentClass = currentClass.getSuperclass();
        }
        throw new FrameworkException("@Application(\"YOUR_APP_NAME\") annotation is not set anywhere on supplied" +
                " class '" + appClass.getName() + "' or its ancestors");
    }

    public static ProxySettings getProxySettings() {
        return proxySettings;
    }
}