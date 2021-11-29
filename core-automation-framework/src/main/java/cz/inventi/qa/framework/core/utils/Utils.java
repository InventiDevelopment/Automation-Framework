package cz.inventi.qa.framework.core.utils;

import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Matcher;

public class Utils {

    public static <T extends Enum<?>> T getEnum(Class<T> enumClass, String findValue) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.name().equalsIgnoreCase(findValue))
                .findAny()
                .orElse(null);
    }

    /**
     * Returns unique identifier for test application instance to
     * allow multi-threaded execution.
     */
    public static String getTestIdentifier() {
        return Thread.currentThread().getName();
    }

    /**
     * Retrieves path to given file in global project test resources folder.
     * Folder is created if it does not exist.
     * @return Path to the file inside test resources folder
     */
    public static Path getTestResourcesFolder() {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        if (!projectPath.endsWith(File.separator)) projectPath += File.separator;
        String testResourcesFolderPath = "../test-resources".replaceAll(
                "([\\\\/])",
                Matcher.quoteReplacement(File.separator)
        );
        File testResourcesFolder = new File(projectPath + testResourcesFolderPath);
        if (!testResourcesFolder.exists()) {
            if (testResourcesFolder.mkdirs()) {
                Log.info("Created test-resources (" + testResourcesFolder.toPath() + ") directory");
            } else {
                throw new FrameworkException("Cannot create test resources directory");
            }
        }
        return testResourcesFolder.toPath().toAbsolutePath();
    }

    /**
     * Starts thread sleep for given amount of time.
     * @param milliseconds amount of milliseconds
     */
    public static void hardSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new FrameworkException("Exception raised while in sleep", e);
        }
    }
}
