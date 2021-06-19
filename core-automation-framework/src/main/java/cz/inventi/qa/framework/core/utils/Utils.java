package cz.inventi.qa.framework.core.utils;

import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.test.TestBase;

import java.util.Arrays;

public class Utils {

    public static <T extends Enum<?>> T getEnum(Class<T> enumClass, String findValue) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(findValue))
                .findAny()
                .orElse(null);
    }

    /**
     * Generates application instance unique identifier according to the name
     * of TestBase class to allow multithreaded execution on the TestBase class
     * level.
     */
    public static String getCallerTestClassName() {
        for (StackTraceElement stackTraceElement :  Thread.currentThread().getStackTrace()) {
            try {
                Class<?> testClass = Class.forName(stackTraceElement.getClassName());
                if (!testClass.equals(TestBase.class) && TestBase.class.isAssignableFrom(testClass)) {
                    return stackTraceElement.getClassName();
                }
            } catch (ClassNotFoundException e) {
                throw new FrameworkException("Could not find TestBase calling class", e);
            }
        }
        return null;
    }
}
