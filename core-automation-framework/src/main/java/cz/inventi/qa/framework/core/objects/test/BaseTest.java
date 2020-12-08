package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.annotations.ConfigFiles;

import java.util.Objects;

public abstract class BaseTest {

    public BaseTest() {
        ConfigFiles configFilesAnnotation = this.getClass().getSuperclass().getDeclaredAnnotation(ConfigFiles.class);

        if (configFilesAnnotation != null) {
            String appConfigPath = getCustomPackageConfigPath(configFilesAnnotation.appConfig(), this.getClass().getSuperclass().getClassLoader());
            String driverConfigPath = getCustomPackageConfigPath(configFilesAnnotation.driverConfig(), this.getClass().getSuperclass().getClassLoader());
            ConfigManager.setCustomAppConfigPath(appConfigPath);
            ConfigManager.setCustomDriverConfigPath(driverConfigPath);
        }
    }

    private static String getCustomPackageConfigPath(String configFileName, ClassLoader classLoader) {
        try {
            return Objects.requireNonNull(classLoader.getResource(configFileName)).getPath();
        } catch (NullPointerException e) {
            Log.fail("Not possible to read from a custom yml file. Check that the '" + configFileName + "' file " +
                    "is created in resources folder in the package you are launching test from.");
        }
        return null;
    }
}
