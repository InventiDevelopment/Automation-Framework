package cz.inventi.qa.framework.core.objects.test.assertions;

import cz.inventi.qa.framework.core.data.enums.test.AssertionType;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Shorthand calls for Assertion functions.
 */
public class Assert extends Assertion {
    private static final AssertionType ASSERTION_TYPE = AssertionType.HARD;

    public static <T> void compareObjectFields(T actual, T expected, String name, String... skipFields) {
        compareObjectFields(actual, expected, name, ASSERTION_TYPE, skipFields);
    }

    public static void assertEqualsRegex(String regex, String actual, String name) {
        assertEqualsRegex(Pattern.compile(regex), actual, name, ASSERTION_TYPE);
    }

    public static void assertEqualsRegex(Pattern pattern, String actual, String name) {
        assertEqualsRegex(pattern, actual, name, ASSERTION_TYPE);
    }

    public static void assertNotEqualsRegex(String regex, String actual, String name) {
        assertNotEqualsRegex(Pattern.compile(regex), actual, name, ASSERTION_TYPE);
    }

    public static void assertNotEqualsRegex(Pattern pattern, String actual, String name) {
        assertNotEqualsRegex(pattern, actual, name, ASSERTION_TYPE);
    }

    public static void assertEqualsIgnoreCase(String actual, String expected, String name) {
        assertEqualsIgnoreCase(actual, expected, name, ASSERTION_TYPE);
    }

    public static void assertEquals(Object actual, Object expected, String name) {
        assertEquals(actual, expected, name, ASSERTION_TYPE);
    }

    public static void assertEqualsPrimitive(Object actual, Object expected, String name) {
        assertEqualsPrimitive(actual, expected, name, ASSERTION_TYPE);
    }

    public static void assertNotEquals(Object actual, Object notEqualTo, String name) {
        assertNotEquals(actual, notEqualTo, name, ASSERTION_TYPE);
    }

    public static void assertNotEqualsPrimitive(Object actual, Object notEqualTo, String name) {
        assertNotEqualsPrimitive(actual, notEqualTo, name, ASSERTION_TYPE);
    }

    public static void assertTrue(boolean condition, String name) {
        assertTrue(condition, name, ASSERTION_TYPE);
    }

    public static void assertFalse(boolean condition, String name) {
        assertFalse(condition, name, ASSERTION_TYPE);
    }

    public static void assertNotNull(Object object, String name) {
        assertNotNull(object, name, ASSERTION_TYPE);
    }

    public static void assertNull(Object object, String name) {
        assertNull(object, name, ASSERTION_TYPE);
    }

    public static <T> void assertJsonEquals(T actual, T expected) {
        assertJsonEquals(actual, expected, ASSERTION_TYPE);
    }

    public static <T> void assertJsonEquals(T actual, T expected, String name) {
        assertJsonEquals(actual, expected, name, ASSERTION_TYPE);
    }

    public static void waitForAssertionToPass(Runnable function, String name, long retries, long retryDelayMs) {
        waitForAssertionToPass(function, name, retries, retryDelayMs, ASSERTION_TYPE);
    }

    public static void assertFileContentEquals(File file1, File file2, String name) {
        assertFileContentEquals(file1, file2, name, ASSERTION_TYPE);
    }
}