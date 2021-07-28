package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.annotations.ConfigFileSpecs;
import cz.inventi.qa.framework.core.data.app.*;
import cz.inventi.qa.framework.core.data.config.AppsConfigData;
import cz.inventi.qa.framework.core.data.config.WebDriverConfigData;
import cz.inventi.qa.framework.core.data.enums.ConfigFile;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigManager {
    private static final String CONFIG_DIRECTORY = "src/main/resources/config/";
    private final ObjectMapper mapper;
    private final Map<ConfigFile, Object> configFiles;
    private final AppInstance appInstance;

    public ConfigManager(AppInstance appInstance) {
        this.appInstance = appInstance;
        configFiles = new HashMap<>();
        mapper = new ObjectMapper(new YAMLFactory());
        setMapperSettings();
        initMandatoryConfigs();
    }

    private void setMapperSettings() {
        mapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
    }

    private void initMandatoryConfigs() {
        Arrays
                .stream(ConfigFile.values())
                .filter(ConfigFile::isMandatory)
                .forEach(this::initConfig);
    }

    public void initConfig(ConfigFile configFile) {
        Class<?> configClass = configFile.getConfigClass();
        String configFileName = getConfigDefaultFileName(configClass);
        String customConfigPath = TestSuiteParameters.getParameter(configFile.getConfigParamName());

        if (customConfigPath != null) {
            configFileName = customConfigPath;
        }

        String configPath = new File(CONFIG_DIRECTORY + configFileName).getAbsolutePath();

        try {
            Log.debug("Loading " + configFile + " YAML configuration file: '" + configPath + "'");
            configFiles.put(configFile, mapper.readValue(new File(Objects.requireNonNull(configPath)), configClass));
            Log.debug(configFile + " YAML configuration files successfully loaded");
            Log.debug(configFile + " YAML config content:\n"
                    + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configFiles.get(configFile)));
        } catch (IOException e) {
            throw new FrameworkException(
                    "Not possible to read from " + configFile + " YML file. Check that file is" +
                    " accessible at following location: '" + configPath + "'",
                    e
            );
        }
    }

    private String getConfigDefaultFileName(Class<?> configClass) {
        ConfigFileSpecs configFileSpecsAnnotation = configClass.getDeclaredAnnotation(ConfigFileSpecs.class);
        if (configFileSpecsAnnotation != null) {
            return configFileSpecsAnnotation.name();
        } else {
            throw new FrameworkException(
                    "Given config file class '" + configClass + "' has no defined @ConfigFile annotation"
            );
        }
    }

    public WebDriverConfigData getWebDriverConfigData() {
        return (WebDriverConfigData) configFiles.get(ConfigFile.WEB_DRIVER_CONFIG);
    }

    public AppsConfigData getAppsConfigData() {
        return (AppsConfigData) configFiles.get(ConfigFile.APPS_CONFIG);
    }

    public WebApplication getCurrentWebAppConfig() {
        return getAppsConfigData().getApplications().getWeb().get(appInstance.getApplicationName());
    }

    public ApiApplication getCurrentApiAppConfig() {
        return getAppsConfigData().getApplications().getApi().get(appInstance.getApplicationName());
    }

    public MobileApplication getCurrentMobileAppConfig() {
        return getAppsConfigData().getApplications().getMobile().get(appInstance.getApplicationName());
    }

    public DesktopApplication getCurrentDesktopAppConfig() {
        return getAppsConfigData().getApplications().getDesktop().get(appInstance.getApplicationName());
    }

    public Application getCurrentAppGeneralConfig() {
        Applications applications = getAppsConfigData().getApplications();
        String currentApplicationName = appInstance.getApplicationName();

        return switch (appInstance.getApplicationType()) {
            case API -> applications.getApi().get(currentApplicationName);
            case DESKTOP -> applications.getDesktop().get(currentApplicationName);
            case MOBILE -> applications.getMobile().get(currentApplicationName);
            case WEB -> applications.getWeb().get(currentApplicationName);
        };
    }

    public String getCurrentApplicationEnvironmentUrl() {
        String environment = TestSuiteParameters.getParameter("environment");
        return getCurrentAppGeneralConfig()
                .getEnvironments()
                .entrySet()
                .stream()
                .filter(map -> map.getKey().toLowerCase().equals(environment.toLowerCase()))
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