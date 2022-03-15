package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.annotations.ConfigFileSpecs;
import cz.inventi.qa.framework.core.data.app.*;
import cz.inventi.qa.framework.core.data.config.AppConfigData;
import cz.inventi.qa.framework.core.data.config.WebDriverConfigData;
import cz.inventi.qa.framework.core.data.enums.ConfigFile;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static final String CONFIG_FILE_FOLDER = "config";
    private final ObjectMapper mapper;
    private final Map<ConfigFile, Object> configFiles;
    private final AppInstance<?> appInstance;

    public ConfigManager(AppInstance<?> appInstance) {
        this.appInstance = appInstance;
        configFiles = new HashMap<>();
        mapper = new ObjectMapper(new YAMLFactory());
        setMapperSettings();
        initMandatoryConfigs();
    }

    private void setMapperSettings() {
        mapper
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    }

    private void initMandatoryConfigs() {
        Arrays
                .stream(ConfigFile.values())
                .filter(configFile -> configFile
                        .getMandatoryForAppType()
                        .contains(appInstance.getApplicationType())
                )
                .forEach(this::initConfig);
    }

    public void initConfig(ConfigFile configFile) {
        Class<?> configClass = configFile.getConfigClass();
        String configFileName = getConfigDefaultFileName(configClass);
        String customConfigPath = TestSuiteParameters.getParameter(configFile.getConfigParamName());
        if (customConfigPath != null) configFileName = customConfigPath;
        String configFilePath = Path.of(
                appInstance.getApplicationName(),
                CONFIG_FILE_FOLDER,
                configFileName
        ).toString();
        URL absoluteConfigFileUrl = getClass().getClassLoader().getResource(configFilePath);
        try {
            Log.debug("Loading " + configFile + " YAML configuration file: '" + absoluteConfigFileUrl + "'");
            configFiles.put(configFile, mapper.readValue(absoluteConfigFileUrl, configClass));
            Log.debug(configFile + " YAML configuration files successfully loaded");
            Log.debug(
                    configFile + " YAML config content:\n" +
                    mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configFiles.get(configFile))
            );
        } catch (IOException | IllegalArgumentException e) {
            throw new FrameworkException(
                    "Not possible to read " + configFile + " YML file. Please check that file '" +
                    configFileName + "' is accessible at current project module's 'src" +
                    File.separator + "main" + File.separator + appInstance.getApplicationName() + File.separator +
                    CONFIG_FILE_FOLDER + "' folder.",
                    e
            );
        }
    }

    private String getConfigDefaultFileName(Class<?> configClass) {
        ConfigFileSpecs configFileSpecsAnnotation = configClass.getDeclaredAnnotation(ConfigFileSpecs.class);
        if (configFileSpecsAnnotation != null) return configFileSpecsAnnotation.name();
        throw new FrameworkException(
                "Given config file class '" + configClass + "' has no defined @ConfigFile annotation"
        );
    }

    public WebDriverConfigData getWebDriverConfigData() {
        return (WebDriverConfigData) configFiles.get(ConfigFile.WEB_DRIVER_CONFIG);
    }

    public AppConfigData getAppsConfigData() {
        return (AppConfigData) configFiles.get(ConfigFile.APPS_CONFIG);
    }

    public WebApplication getCurrentWebAppConfig() {
        return verifyConfigExists(getAppsConfigData()
                .getApplicationConfig()
                .getWeb()
                .get(appInstance.getApplicationName())
        );
    }

    public ApiApplication getCurrentApiAppConfig() {
        return verifyConfigExists(getAppsConfigData()
                .getApplicationConfig()
                .getApi()
                .get(appInstance.getApplicationName())
        );
    }

    public MobileApplication getCurrentMobileAppConfig() {
        return verifyConfigExists(getAppsConfigData()
                .getApplicationConfig()
                .getMobile()
                .get(appInstance.getApplicationName())
        );
    }

    public DesktopApplication getCurrentDesktopAppConfig() {
        return verifyConfigExists(getAppsConfigData()
                .getApplicationConfig()
                .getDesktop()
                .get(appInstance.getApplicationName())
        );
    }

    private <T> T verifyConfigExists(T webApplication) {
        if (webApplication == null) {
            throw new FrameworkException(
                    "Configuration for application '" + appInstance.getApplicationName() + "' could not be found. " +
                    "Please verify that you entered correct application name into you application starting class."
                    + appInstance.getApplicationStartingClassInitialized().getClass()
            );
        }
        return webApplication;
    }

    public Application getCurrentAppGeneralConfig() {
        Application appGeneralConfig = null;
        switch (appInstance.getApplicationType()) {
            case API -> appGeneralConfig = getCurrentApiAppConfig();
            case DESKTOP -> appGeneralConfig = getCurrentDesktopAppConfig();
            case MOBILE -> appGeneralConfig = getCurrentMobileAppConfig();
            case WEB -> appGeneralConfig = getCurrentWebAppConfig();
        }
        return appGeneralConfig;
    }

    public String getCurrentApplicationEnvironmentUrl() {
        String environment = TestSuiteParameters.getParameter("environment");
        return getCurrentAppGeneralConfig()
                .getEnvironments()
                .entrySet()
                .stream()
                .filter(map -> map.getKey().equalsIgnoreCase(environment))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "URL of application: '" +
                        appInstance.getApplicationName() + "' with env: '" + environment + "'" +
                        " could not be found in YAML. Please check config and definition of init class."
                    )
                );
    }
}