package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.data.config.AppConfigData;
import cz.inventi.qa.framework.core.data.config.WebDriverConfigData;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ConfigManager {
    private static final String CONFIG_DIRECTORY = "config/";
    private static final String RESOURCES_DIRECTORY = "src/main/resources";
    private static final String WEBDRIVER_CONFIG_FILE_NAME = CONFIG_DIRECTORY + "webDriverConfig.yml";
    private static final String APP_CONFIG_FILE_NAME = CONFIG_DIRECTORY + "appsConfig.yml";
    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final AppInstance appInstance;
    private AppConfigData appsConfigData;
    private WebDriverConfigData webDriverConfigData;
    private String customAppConfigPath;
    private String customDriverConfigPath;

    public ConfigManager(AppInstance appInstance) {
        this.appInstance = appInstance;
    }

    public void initWebConfigs() {
        Log.debug("Initializing configuration files for WEB testing");
        initGeneralAppConfig();
        initWebDriverConfig();
    }

    public void initApiConfigs() {
       Log.debug("Initializing configuration files for API testing");
       initGeneralAppConfig();
    }

    private static String getCustomPackageConfigPath(String configFileName) {
        File customConfigFile = new File(RESOURCES_DIRECTORY + "/" + CONFIG_DIRECTORY + configFileName);

        try {
            return Objects.requireNonNull(customConfigFile.getAbsolutePath());
        } catch (NullPointerException e) {
            Log.fail("Not possible to read from a custom yml file '" + configFileName + "' at '" + customConfigFile.getAbsolutePath() + "'. Check that the '" + configFileName + "' file " +
                    "is created in resources folder in the package you are launching test from.\nStacktrace: " + e);
        }
        return null;
    }

    private void initGeneralAppConfig() {
        String appConfigPath = new File(RESOURCES_DIRECTORY + "/" + APP_CONFIG_FILE_NAME).getAbsolutePath();

        if (customAppConfigPath != null) {
            appConfigPath = getCustomPackageConfigPath(customAppConfigPath);
        }

        try {
            Log.debug("Loading APP YAML configuration file: '" + appConfigPath + "'");
            appsConfigData = mapper.readValue(new File(appConfigPath), AppConfigData.class);
            Log.debug("APP YAML configuration file successfully loaded");
            Log.debug("APP YAML config content:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appsConfigData));
        } catch (IOException e) {
            Log.fail("Not possible to read from APP YML file. Check that file is accessible at following location: '" + appConfigPath + "'", e);
        }
    }

    private void initWebDriverConfig () {
        String driverConfigPath = new File(RESOURCES_DIRECTORY + "/" + WEBDRIVER_CONFIG_FILE_NAME).getAbsolutePath();

        if (customDriverConfigPath != null) {
            driverConfigPath = getCustomPackageConfigPath(customDriverConfigPath);
        }

        try {
            Log.debug("Loading WEBDRIVER YAML configuration file: '" + driverConfigPath + "'");
            webDriverConfigData = mapper.readValue(new File(driverConfigPath), WebDriverConfigData.class);
            Log.debug("WEBDRIVER YAML configuration files successfully loaded");
            Log.debug("WEBDRIVER YAML config content:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(webDriverConfigData));
        } catch (IOException e) {
            Log.fail("Not possible to read from WEBDRIVER YML files. Check that file is accessible at following location: '" + driverConfigPath + "'\n", e);
        }
    }

    public WebDriverConfigData getWebDriverConfigData() {
        return webDriverConfigData;
    }

    public AppConfigData getAppsConfigData() {
        return appsConfigData;
    }

    public void setCustomAppConfigPath (String customAppConfigPath) {
        appInstance.getConfigManager().customAppConfigPath = customAppConfigPath;
    }

    public void setCustomWebDriverConfigPath(String customDriverConfigPath) {
        appInstance.getConfigManager().customDriverConfigPath = customDriverConfigPath;
    }

    public String getCustomAppConfigPath() {
        return customAppConfigPath;
    }

    public String getCustomWebDriverConfigPath() {
        return customDriverConfigPath;
    }

}
