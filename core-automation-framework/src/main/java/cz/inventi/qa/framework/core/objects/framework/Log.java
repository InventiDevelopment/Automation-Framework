package cz.inventi.qa.framework.core.objects.framework;

import cz.inventi.qa.framework.core.data.enums.RunMode;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Log {
    public static final String LOGGER_NAME = "cz.inventi.qa.framework";

    public static void info(String text) {
        getLogger().info(text);
    }

    public static void warn(String text) {
        getLogger().warn(text);
    }

    public static void debug(String text) {
        getLogger().debug(text);
    }

    public static Logger getLogger() {
        return LogManager.getLogger(LOGGER_NAME);
    }

    public static void setGlobalLogLevel(RunMode runMode) {
        Level logLevel = switch (runMode) {
            case DEBUG -> Level.DEBUG;
            case NORMAL -> Level.INFO;
            default -> Level.ERROR;
        };
        Log.info("Setting global log level to '" + logLevel + "'");
        LogManager.getRootLogger().setLevel(logLevel);
    }
}
