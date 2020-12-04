package cz.inventi.qa.framework.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
    private static final Logger logger = LogManager.getLogger("CoreLogger");

    public static void info(String text) {
        logger.info(text);
    }

    public static void warn(String text) {
        logger.warn(text);
    }

    public static void fail(String text, Throwable e)  {
        logger.error(text);
        fail(e);
    }

    public static void fail(Throwable e) {
        logger.error("Test failed, please see stacktrace: ");
        e.printStackTrace();
        throw new RuntimeException();
    }

    public static void fail(String text) {
        fail(text, new RuntimeException(text));
    }

    public static void debug(String text) {
        logger.debug(text);
    }

}
