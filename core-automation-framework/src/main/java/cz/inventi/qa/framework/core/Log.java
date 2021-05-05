package cz.inventi.qa.framework.core;

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

    public static void fail(String text, Throwable e)  {
        getLogger().error(text);
        fail(e);
    }

    public static void fail(Throwable e) {
        getLogger().error("Test failed, please see stacktrace: ");
        throw new RuntimeException(e);
    }

    public static void fail(String text) {
        fail(text, new RuntimeException(text));
    }

    public static void debug(String text) {
        getLogger().debug(text);
    }

    public static Logger getLogger() {
        return LogManager.getLogger(LOGGER_NAME);
    }

    public static void setLogLevel(RunMode runMode) {
        Level logLevel = Level.ERROR;
        switch (runMode) {
            case DEBUG:
                logLevel = Level.DEBUG;
            case NORMAL:
                logLevel = Level.ERROR;
        }
        getLogger().setLevel(logLevel);
    }
}
