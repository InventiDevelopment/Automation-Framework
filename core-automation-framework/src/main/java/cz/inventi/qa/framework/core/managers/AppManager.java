package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.data.app.Applications;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.Map;

public class AppManager {
    private static AppManager appManager;
    private static String appUrl;
    private static ApplicationType currentApplicationType;
    private static String currentApplicationName;

    public static <T extends WebPage> void initWebApplication(Class<T> klass) {
        setCurrentApplication(klass, ApplicationType.WEB);
        WebDriverManager.init();
    }

    public static <T extends Api> void initApiApplication(Class<T> klass) {
        setCurrentApplication(klass, ApplicationType.API);
    }

    private static void setCurrentApplication(Class<?> klass, ApplicationType applicationType) {
        currentApplicationType = applicationType;
        checkAppAnnotationPresent(klass);
        initializeEnvironment(klass.getAnnotation(Application.class));
    }

    public static <T extends cz.inventi.qa.framework.core.data.app.Application> T getCurrentApplication () {
        Applications applications = ConfigManager.getAppsConfigData().getApplications();

        switch (currentApplicationType) {
            case API:
                return (T) applications.getApi().get(currentApplicationName);
            case DESKTOP:
                return (T) applications.getDesktop().get(currentApplicationName);
            case MOBILE:
                return (T) applications.getMobile().get(currentApplicationName);
            case WEB:
                return (T) applications.getWeb().get(currentApplicationName);
            default:
                return null;
        }
    }

    private static void checkAppAnnotationPresent(Class<?> klass) {
        Application applicationAnnotation = klass.getAnnotation(Application.class);

        if (applicationAnnotation == null) {
            Log.fail("@Application() annotation is not set on your initial class '" + klass.getName() + "'");
        }
        currentApplicationName = applicationAnnotation.name();
    }

    private static void initializeEnvironment (Application applicationAnnotation) {
        String environment = ParametersManager.getCommonParameters().getEnvironment();
        Map<String, String> currentAppEnvironments = getCurrentApplication().getEnvironments();

        appUrl = currentAppEnvironments
                .entrySet()
                .stream()
                .filter(map -> map.getKey().toLowerCase().equals(environment.toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("URL of application: '" + currentApplicationName + "' with env: '" + environment + "' cannot be found in YAML. Please check config and definition of init class."));
    }

    public AppManager getAppManager() {
        if (appManager == null) {
            appManager = new AppManager();
        }
        return appManager;
    }

    public static String getAppUrl() {
        return appUrl;
    }

    public static ApplicationType getCurrentApplicationType() {
        return currentApplicationType;
    }

    public static void setCurrentApplicationType(ApplicationType currentApplicationType) {
        AppManager.currentApplicationType = currentApplicationType;
    }

    public static String getCurrentApplicationName() {
        return currentApplicationName;
    }
}
