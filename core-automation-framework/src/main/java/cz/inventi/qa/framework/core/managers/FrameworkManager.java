package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.data.config.ProxySettings;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.data.enums.ProxyScheme;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.api.ApiAppInstance;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.test.TestRun;
import cz.inventi.qa.framework.core.objects.web.WebAppInstance;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.framework.core.utils.Utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FrameworkManager {
    private volatile static FrameworkManager frameworkManager = null;
    private final Map<String, TestRun> testRuns;
    private static RunMode runMode;
    private static ProxySettings proxySettings;

    public FrameworkManager() {
        this.testRuns = new HashMap<>();
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

    public <T extends WebPage> T initWebAppAt(Class<T> webPage, String testIdentifier) {
        return retrieveOrInitializeWebAppInstance(webPage, testIdentifier).retrieveOrInitWebPage(webPage);
    }

    public <T extends Api> T initApiAppAt(Class<T> api, String testIdentifier) {
        return retrieveOrInitializeApiAppInstance(api, testIdentifier).retrieveOrInitApi(api);
    }

    public static Map<String, TestRun> getTestRuns() {
        return getInstance().testRuns;
    }

    public static TestRun getCurrentTestRun() {
        return getTestRuns().get(Utils.getTestIdentifier());
    }

    synchronized public static FrameworkManager getInstance() {
        if (frameworkManager == null) frameworkManager = new FrameworkManager();
        return frameworkManager;
    }

    public static void quitCurrentTestRun() {
        Log.info("Quitting TestRun (" + Utils.getTestIdentifier() + ")");
        getCurrentTestRun().quit(Utils.getTestIdentifier());
        getTestRuns().remove(Utils.getTestIdentifier());
        Log.info("TestRun (" + Utils.getTestIdentifier() + ") successfully quit");
        Log.info(getTestRuns().size() + " TestRun instances left in the stack");
    }

    private static Map<String, WebAppInstance<?>> getTestRunWebAppInstances(String testIdentifier) {
        return getTestRuns().get(testIdentifier).getWebAppInstances();
    }

    private static Map<String, ApiAppInstance<?>> getTestRunApiAppInstances(String testIdentifier) {
        return getTestRuns().get(testIdentifier).getApiAppInstances();
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

    @SuppressWarnings("unchecked")
    private synchronized <T extends WebPage> WebAppInstance<T> retrieveOrInitializeWebAppInstance(
            Class<T> appClass,
            String testIdentifier
    ) {
        manageTestRun(testIdentifier);
        String appName = validateAndGetAppName(appClass);
        Map<String, WebAppInstance<?>> webAppInstances = getTestRunWebAppInstances(testIdentifier);
        if (!webAppInstances.containsKey(appName)) {
            logCreatingAppInstance(appClass.getName(), testIdentifier, ApplicationType.WEB);
            WebAppInstance<T> appInstance = new WebAppInstance<T>(getApplicationType(appClass), appName);
            webAppInstances.put(appName, appInstance);
            logFinishedCreatingAppInstance(testIdentifier, ApplicationType.WEB);
        }
        return (WebAppInstance<T>) webAppInstances.get(appName);
    }

    @SuppressWarnings("unchecked")
    private synchronized <T extends Api> ApiAppInstance<T> retrieveOrInitializeApiAppInstance(
            Class<T> appClass,
            String testIdentifier
    ) {
        manageTestRun(testIdentifier);
        String appName = validateAndGetAppName(appClass);
        Map<String, ApiAppInstance<?>> apiAppInstances = getTestRunApiAppInstances(testIdentifier);
        if (!apiAppInstances.containsKey(appName)) {
            logCreatingAppInstance(appClass.getName(), testIdentifier, ApplicationType.API);
            ApiAppInstance<T> appInstance = new ApiAppInstance<T>(getApplicationType(appClass), appName);
            apiAppInstances.put(appName, appInstance);
            logFinishedCreatingAppInstance(testIdentifier, ApplicationType.API);
        }
        return (ApiAppInstance<T>) apiAppInstances.get(appName);
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

    private void logFinishedCreatingAppInstance(String testIdentifier, ApplicationType type) {
        Log.info("Successfully created new " + type + " AppInstance for test thread (" + testIdentifier + ")");
    }

    private void logCreatingAppInstance(String name, String testIdentifier, ApplicationType type) {
        Log.info("Creating " + type + " AppInstance (" + name + ") for test thread (" + testIdentifier + ")");
    }

    private void manageTestRun(String testIdentifier) {
        testRuns.computeIfAbsent(testIdentifier, k -> new TestRun());
    }
}