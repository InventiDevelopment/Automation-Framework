package cz.inventi.sample.framework.core.managers;

import cz.inventi.sample.framework.core.Log;
import cz.inventi.sample.framework.core.data.app.Environments;
import cz.inventi.sample.framework.core.objects.web.WebPage;
import cz.inventi.sample.framework.core.annotations.Application;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class AppManager {
    private static AppManager appManager;
    private static String appUrl;

    public static <T extends WebPage> void initApplication(Class<T> klass) {
        WebDriver driver = DriverManager.getDriver();
        Application application = klass.getAnnotation(Application.class);

        if (application == null) {
            Log.fail("@Application() is not set above class in your init PageObject");
        }

        String environment = ParametersManager.getEnvironment();
        String applicationName = Objects.requireNonNull(application).value();

        Map<String, Environments> appMap = ConfigManager.getAppConfigData().getApplications();
        appUrl = appMap.entrySet()
                .stream()
                .filter(map -> map.getKey().toLowerCase().equals(applicationName.toLowerCase()))
                .map(Map.Entry::getValue)
                .flatMap(e -> e.getEnvironments().entrySet().stream())
                .filter(map -> map.getKey().toLowerCase().equals(environment.toLowerCase()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("URL of application: '" + applicationName + "' with env: '" + environment + "' is not found in YAML. Please check config and definition of init class"));

        try {
            formatResourcesURL();
            driver.get(appUrl);
        } catch (Exception e) {
            Log.fail("Unable to get url: '" + appUrl + "'. Please check that url is valid.");
        }
    }

    private static void formatResourcesURL () {
        if (appUrl.contains("test://") || appUrl.contains("main://")) {
            String[] resourcePackage = appUrl.split("://");
            appUrl = new File(Paths.get("src",resourcePackage[0], "resources") + "/" + resourcePackage[1]).getAbsolutePath();
        }
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
}
