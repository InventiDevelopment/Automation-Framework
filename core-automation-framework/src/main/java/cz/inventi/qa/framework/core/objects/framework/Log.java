package cz.inventi.qa.framework.core.objects.framework;

import cz.inventi.qa.framework.core.data.enums.RunMode;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;


public class Log {
    public static final String LOGGER_NAME = "cz.inventi.qa.framework";

    public static void info(String text) {
        getLogger().info(text);
    }

    public static void error(String text) {
        getLogger().error(text);
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
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        ctx.getConfiguration().getLoggerConfig(LogManager.ROOT_LOGGER_NAME).setLevel(logLevel);
        ctx.updateLoggers();
    }
}
