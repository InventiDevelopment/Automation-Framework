package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.Utils;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.data.web.Timeouts;
import cz.inventi.qa.framework.core.objects.config.AppConfigData;
import cz.inventi.qa.framework.core.objects.config.DriverConfigData;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ConfigManager {

    private static final String CONFIG_DIRECTORY = "config/";
    private static final String DRIVER_CONFIG_FILE_NAME = CONFIG_DIRECTORY + "driverConfig.yml";
    private static final String APP_CONFIG_FILE_NAME =  CONFIG_DIRECTORY + "appConfig.yml";
    private static final String PATH_TO_APP_CONFIG = Objects.requireNonNull(ConfigManager.class.getClassLoader().getResource(APP_CONFIG_FILE_NAME)).getPath();
    private static final String PATH_TO_DRIVER_CONFIG = Objects.requireNonNull(ConfigManager.class.getClassLoader().getResource(DRIVER_CONFIG_FILE_NAME)).getPath();
    private static AppConfigData appConfigData;
    private static DriverConfigData driverConfigData;
    private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private static String customAppConfigPath;
    private static String customDriverConfigPath;

    public static void init() {
        String appConfigPath = PATH_TO_APP_CONFIG;
        String driverConfigPath = PATH_TO_DRIVER_CONFIG;

        if (customAppConfigPath != null) {
            appConfigPath = customAppConfigPath;
        }

        if (customDriverConfigPath != null) {
            driverConfigPath = customDriverConfigPath;
        }

        appConfigPath = Utils.getFilePathDecoded(appConfigPath);
        driverConfigPath = Utils.getFilePathDecoded(driverConfigPath);

        try {
            Log.debug("Loading YAML configuration files:\n- APP: '" + appConfigPath + "'\n- DRIVER: '" + driverConfigPath + "'\n");
            appConfigData = mapper.readValue(new File(appConfigPath), AppConfigData.class);
            driverConfigData = mapper.readValue(new File(driverConfigPath), DriverConfigData.class);
            Log.debug("YAML app configuration files successfully loaded");
            Log.debug("YAML APP config content:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appConfigData));
            Log.debug("YAML DRIVER config content:\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(driverConfigData));
        } catch (IOException e) {
            Log.fail("Not possible to read from YML files. Check that files are accessible on following locations:\n- APP: " + appConfigPath + "'\n- DRIVER: '" + driverConfigPath + "'\n", e);
        }
    }

    public static DriverConfigData getDriverConfigData () {
        return driverConfigData;
    }

    public static AppConfigData getAppConfigData () {
        return appConfigData;
    }

    public static void setCustomAppConfigPath (String customAppConfigPath) {
        ConfigManager.customAppConfigPath = customAppConfigPath;
    }

    public static void setCustomDriverConfigPath (String customDriverConfigPath) {
        ConfigManager.customDriverConfigPath = customDriverConfigPath;
    }

    public static String getCustomAppConfigPath() {
        return customAppConfigPath;
    }

    public static String getCustomDriverConfigPath() {
        return customDriverConfigPath;
    }

    public static RunMode getRunMode() {
        return RunMode.valueOf(driverConfigData.getGeneralSettings().getRunMode().toUpperCase());
    }

    public static boolean driverWaitsAutomatically() {
        return driverConfigData.getGeneralSettings().getWait().waitsAutomatically();
    }

    public static Timeouts getTimeouts() {
        return driverConfigData.getGeneralSettings().getWait().getTimeouts();
    }
}
