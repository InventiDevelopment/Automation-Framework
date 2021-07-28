package cz.inventi.qa.framework.core.data.enums;

import cz.inventi.qa.framework.core.data.config.AppsConfigData;
import cz.inventi.qa.framework.core.data.config.WebDriverConfigData;

/**
 * Configuration objects for the framework.
 */
public enum ConfigFile {
    WEB_DRIVER_CONFIG(WebDriverConfigData.class, true, "webDriverConfig"),
    APPS_CONFIG(AppsConfigData.class, true, "appsConfig");

    private final Class<?> configClass;
    private final boolean mandatory;
    private final String configParamName;

    /**
     * @param configClass configuration file class (to be mapped by Jackson)
     * @param mandatory if the class should be loaded by default with the framework
     * @param configParamName parameter name that will contain config file path/name
     */
    ConfigFile(Class<?> configClass, boolean mandatory, String configParamName) {
        this.configClass = configClass;
        this.mandatory = mandatory;
        this.configParamName = configParamName;
    }

    public Class<?> getConfigClass() {
        return configClass;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public String getConfigParamName() {
        return configParamName;
    }
}
