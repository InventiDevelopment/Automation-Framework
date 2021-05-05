package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.data.app.Applications;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.Map;

public class AppManager {
    private final AppInstance appInstance;
    private String appUrl;
    private ApplicationType currentApplicationType;
    private String currentApplicationName;

    public AppManager(AppInstance appInstance) {
        this.appInstance = appInstance;
    }

    public <T extends WebPage> void initWebApplication(Class<T> klass) {
        setCurrentApplication(klass, ApplicationType.WEB);
    }

    public <T extends Api> void initApiApplication(Class<T> klass) {
        setCurrentApplication(klass, ApplicationType.API);
    }

    private void setCurrentApplication(Class<?> klass, ApplicationType applicationType) {
        currentApplicationType = applicationType;
        currentApplicationName = klass.getAnnotation(Application.class).name();
        initializeEnvironment();
    }

    @SuppressWarnings("unchecked")
    public <T extends cz.inventi.qa.framework.core.data.app.Application> T getCurrentApplication () {
        Applications applications = appInstance.getConfigManager().getAppsConfigData().getApplications();

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

    private void initializeEnvironment () {
        String environment = appInstance.getParametersManager().getCommonParameters().getEnvironment();
        Map<String, String> currentAppEnvironments = getCurrentApplication().getEnvironments();

        appUrl = currentAppEnvironments
                .entrySet()
                .stream()
                .filter(map -> map.getKey().toLowerCase().equals(environment.toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("URL of application: '" + currentApplicationName + "' with env: '" + environment + "' cannot be found in YAML. Please check config and definition of init class."));
    }

    public String getAppUrl() {
        return appUrl;
    }

    public ApplicationType getCurrentApplicationType() {
        return currentApplicationType;
    }

    public void setCurrentApplicationType(ApplicationType currentApplicationType) {
        appInstance.getAppManager().currentApplicationType = currentApplicationType;
    }

    public String getCurrentApplicationName() {
        return currentApplicationName;
    }
}
