package cz.inventi.qa.framework.core.utils;

import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;

public class Utils {
    public static final String TEST_RESOURCES_FOLDER = "test-resources";

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
     * Retrieves absolute path to given file in global project test resources folder.
     * Folder is created if it does not exist.
     * @return Path to the file inside test resources folder
     */
    public static Path getTestResourcesFolder() {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        if (!projectPath.endsWith(File.separator)) projectPath += File.separator;
        File testResourcesFolder = new File(projectPath + getTestResourcesFolderRelative());
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
     * Retrieves relative path to given file in global project test resources folder.
     * Folder is created if it does not exist.
     * @return Path to the file inside test resources folder
     */
    public static Path getTestResourcesFolderRelative() {
        return Path.of(
                "../",
                TEST_RESOURCES_FOLDER.replaceAll("([\\\\/])", Matcher.quoteReplacement(File.separator))
        );
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

    public static String decodeBase64(String toBeDecoded) {
        return new String(Base64.getDecoder().decode(toBeDecoded));
    }

    public static String encodeBase64(String toBeEncoded) {
        return Base64.getEncoder().encodeToString(toBeEncoded.getBytes());
    }
}
