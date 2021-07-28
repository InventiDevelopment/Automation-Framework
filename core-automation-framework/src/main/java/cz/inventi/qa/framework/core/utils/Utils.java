package cz.inventi.qa.framework.core.utils;

import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.test.TestBase;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    /**
     * Retrieves path to given file in global project test resources folder.
     * Folder is created if it does not exist.
     * @return Path to the file inside test resources folder
     */
    public static Path getTestResourcesFolder() {
        File testResourcesFolder = new File(
                Paths.get("").toAbsolutePath() + "/" + System.getProperty("test.resources.directory")
        );
        if (!testResourcesFolder.exists()) {
            if (testResourcesFolder.mkdirs()) {
                Log.info("Created test-resources (" + testResourcesFolder.toPath() + ") directory");
            } else {
                throw new FrameworkException("Cannot create test resources directory");
            }
        } else {
            return testResourcesFolder.toPath().toAbsolutePath();
        }
        return null;
    }
}
