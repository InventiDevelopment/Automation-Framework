package cz.inventi.qa.framework.core.data.enums;

import cz.inventi.qa.framework.core.annotations.ConfigFileSpecs;
import cz.inventi.qa.framework.core.data.config.AppConfigData;
import cz.inventi.qa.framework.core.data.config.WebDriverConfigData;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;

import java.util.List;

/**
 * Configuration objects for the framework.
 */
public enum ConfigFile {
    WEB_DRIVER_CONFIG(
            WebDriverConfigData.class,
            List.of(ApplicationType.WEB),
            "webDriverConfig"
    ),
    APPS_CONFIG(
            AppConfigData.class,
            List.of(ApplicationType.WEB, ApplicationType.API, ApplicationType.DESKTOP, ApplicationType.MOBILE),
            "appConfig"
    );

    private final Class<?> configClass;
    private final List<ApplicationType> mandatoryForAppType;
    private final String configParamName;

    /**
     * @param configClass configuration file class (to be mapped by Jackson)
     * @param mandatoryForAppType lists application types for which this config file will be checked
     * @param configParamName parameter name that will contain config file path/name
     */
    ConfigFile(
            Class<?> configClass,
            List<ApplicationType> mandatoryForAppType,
            String configParamName
    ) {
        this.configClass = configClass;
        this.mandatoryForAppType = mandatoryForAppType;
        this.configParamName = configParamName;
    }

    public Class<?> getConfigClass() {
        return configClass;
    }

    public List<ApplicationType> getMandatoryForAppType() {
        return mandatoryForAppType;
    }

    public String getConfigParamName() {
        return configParamName;
    }

    public String getConfigDefaultFileName() {
        ConfigFileSpecs configFileSpecsAnnotation = configClass.getDeclaredAnnotation(ConfigFileSpecs.class);
        if (configFileSpecsAnnotation != null) return configFileSpecsAnnotation.name();
        throw new FrameworkException(
                "Given config file class '" + configClass + "' has no defined @ConfigFile annotation"
        );
    }
}
