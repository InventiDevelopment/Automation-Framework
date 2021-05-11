package cz.inventi.qa.framework.core.data.enums;

import cz.inventi.qa.framework.core.data.config.AppsConfigData;
import cz.inventi.qa.framework.core.data.config.WebDriverConfigData;

/**
 * Configuration objects for the framework.
 */
public enum ConfigFile {
    WEBDRIVERCONFIG(WebDriverConfigData.class, true),
    APPSCONFIG(AppsConfigData.class, true);

    private final Class<?> configClass;
    private final boolean mandatory;

    /**
     * @param configClass configuration file class (to be mapped by Jackson)
     * @param mandatory if the class should be loaded by default with the framework
     */
    ConfigFile(Class<?> configClass, boolean mandatory) {
        this.configClass = configClass;
        this.mandatory = mandatory;
    }

    public Class<?> getConfigClass() {
        return configClass;
    }

    public boolean isMandatory() {
        return mandatory;
    }
}
