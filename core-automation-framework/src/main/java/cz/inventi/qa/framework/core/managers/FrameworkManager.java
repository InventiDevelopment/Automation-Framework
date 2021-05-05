package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.data.enums.api.ApiMandatoryParameters;
import cz.inventi.qa.framework.core.data.enums.web.WebMandatoryParameters;
import cz.inventi.qa.framework.core.factories.api.ApiObjectFactory;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FrameworkManager {
    private final Map<String, AppInstance> appInstances;
    private static FrameworkManager frameworkManager = null;
    private static RunMode runMode;

    public FrameworkManager() {
        Log.info("Loading Inventi Automation Framework");
        this.appInstances = new HashMap<>();
        setRunMode();
    }

    public static FrameworkManager getInstance() {
        if (frameworkManager == null) frameworkManager = new FrameworkManager();
        return frameworkManager;
    }

    public <T extends WebPage> T initWebAppInstance(Class<T> startingWebPage, ConfigFiles configFiles) {
        checkMandatoryParametersAreSet(WebMandatoryParameters.class);
        AppInstance initializedApp = getAppInstance(validateAndGetAppName(startingWebPage));
        if (initializedApp == null) {
            return createNewAppInstance(startingWebPage, configFiles)
                    .initWebApp(
                            TestSuiteParameters.getParameter("browser"),
                            TestSuiteParameters.getParameter("environment"),
                            TestSuiteParameters.getParameter("language"),
                            startingWebPage
                    );
        } else {
            return WebObjectFactory.initPage(startingWebPage, initializedApp);
        }
    }

    public <T extends Api> T initApiAppInstance(Class<T> api, ConfigFiles configFiles) {
        checkMandatoryParametersAreSet(ApiMandatoryParameters.class);
        AppInstance initializedApp = getAppInstance(validateAndGetAppName(api));
        if (initializedApp == null) {
            return createNewAppInstance(api, configFiles)
                    .initApiApp(TestSuiteParameters.getParameter("environment"), api);
        } else {
            return ApiObjectFactory.initApi(api, initializedApp);
        }
    }

    public Map<String, AppInstance> getAppInstances() {
        return appInstances;
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
        Log.debug("Setting framework's run mode to '" + runMode + "'");
        Log.setLogLevel(runMode);
    }

    private AppInstance createNewAppInstance(Class<?> startingAppClass, ConfigFiles configFiles) {
        AppInstance appInstance = new AppInstance();
        String appName = validateAndGetAppName(startingAppClass);

        Log.debug("Creating new application instance of '" + startingAppClass + "'");

        if (configFiles != null && !"".equals(configFiles.appsConfig())) {
            appInstance.getConfigManager().setCustomAppConfigPath(configFiles.appsConfig());
        }

        if (configFiles != null && !"".equals(configFiles.driverConfig())) {
            appInstance.getConfigManager().setCustomWebDriverConfigPath(configFiles.driverConfig());
        }

        appInstances.put(appName, appInstance);
        return appInstance;
    }

    private void checkMandatoryParametersAreSet(Class<? extends Enum<?>> mandatoryParamsEnum) {
        List<String> mandatoryParameters = Stream
                .of(mandatoryParamsEnum.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (String mandatoryParameter : mandatoryParameters) {
            if (TestSuiteParameters.getParameter(mandatoryParameter.toLowerCase()) == null) {
                throw new RuntimeException("Parameter '" + mandatoryParameter.toLowerCase() + "' has to be supplied " +
                        "either in the TestNG suite or as a Maven parameter to start this type of application.");
            }
        }
    }

    private AppInstance getAppInstance(String appName) {
        return appInstances.getOrDefault(appName, null);
    }

    private String validateAndGetAppName(Class<?> appClass) {
        Application applicationAnnotation = appClass.getDeclaredAnnotation(Application.class);
        if (applicationAnnotation == null) {
            throw new RuntimeException("@Application() annotation is not set on your initial class '" + appClass.getName() + "'");
        }
        return applicationAnnotation.name();
    }
}