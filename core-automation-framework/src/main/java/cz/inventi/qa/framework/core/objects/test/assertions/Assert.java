package cz.inventi.qa.framework.core.objects.test.assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.inventi.qa.framework.core.objects.framework.FrameworkAssertionException;
import cz.inventi.qa.framework.core.utils.ApiUtils;
import cz.inventi.qa.framework.core.utils.Utils;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Decorator for TestNG assert so that steps can be recorded
 * in the Allure report.
 */
public class Assert {

    public static void assertEqualsRegex(String regex, String actual, String name) {
        try {
            Matcher matcher = Pattern.compile(regex).matcher(actual);
            org.testng.Assert.assertTrue(matcher.matches());
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertNotEqualsRegex(String regex, String actual, String name) {
        try {
            Matcher matcher = Pattern.compile(regex).matcher(actual);
            org.testng.Assert.assertFalse(matcher.matches());
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertEqualsIgnoreCase(String actual, String expected, String name) {
        try {
            org.testng.Assert.assertEquals(actual.toLowerCase(), expected.toLowerCase());
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertEquals(Object actual, Object expected, String name) {
        try {
            org.testng.Assert.assertEquals(actual, expected);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertNotEquals(Object actual, Object notEqualTo, String name) {
        try {
            org.testng.Assert.assertNotEquals(actual, notEqualTo);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertTrue(boolean condition, String name) {
        try {
            org.testng.Assert.assertTrue(condition, name);
            createStepWithStatus(name,  Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertFalse(boolean condition, String name) {
        try {
            org.testng.Assert.assertFalse(condition);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertNotNull(Object object, String name) {
        try {
            org.testng.Assert.assertNotNull(object);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    public static void assertNull(Object object, String name) {
        try {
            org.testng.Assert.assertNull(object);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkAssertionException(e);
        }
    }

    /**
     * Compares equality of objects in serialized JSON format.
     * @param actual Dto of the first object
     * @param expected Dto of the second object
     * @param <T> Type of the Dto
     */
    public static <T> void assertJsonEquals(T actual, T expected) {
        assertJsonEquals(actual, expected, "Compare two JSON objects of '" + actual.getClass() + "'");
    }

    /**
     * Compares equality of objects in serialized JSON format.
     * @param actual Dto of the first object
     * @param expected Dto of the second object
     * @param <T> Type of the Dto
     */
    public static <T> void assertJsonEquals(T actual, T expected, String name) {
        ObjectMapper mapper = ApiUtils.MAPPER.configure(
                SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                true
        );
        String serializedObject1 = null;
        String serializedObject2 = null;
        try {
            serializedObject1 = mapper.writeValueAsString(actual);
            serializedObject2 = mapper.writeValueAsString(expected);
        } catch (JsonProcessingException e) {
            throw new FrameworkAssertionException("Could not serialize objects to JSON for comparison", e);
        }
        if (serializedObject1 != null && serializedObject2 != null) {
            assertEquals(serializedObject1, serializedObject2, name);
        }
    }

    /**
     * Creates step in Allure report for assertion.
     * @param stepName Name of the step
     * @param status Status of the step
     * @param comparedItems Items to be compared
     * @return Allure StepResult
     */
    private static StepResult createStepWithStatus(String stepName, Status status, Object... comparedItems) {
        Optional<String> parentStep = Allure.getLifecycle().getCurrentTestCaseOrStep();
        StepResult stepResult = new StepResult();
        stepResult.setName(stepName + " (assertion)");
        Allure.getLifecycle().startStep(
                parentStep.orElseThrow(),
                RandomStringUtils.randomAlphanumeric(120),
                stepResult
        );
        stepResult.setStatus(status);
        Allure.getLifecycle().stopStep();
        return stepResult;
    }

    /**
     * Retries given function with assertion for given amount
     * of retries and waits for it to pass.
     * @param function lambda function containing assertion using FrameworkAssertionException
     * @param retries number of retries
     * @param retryDelayMs time for delay after retry
     */
    public static void waitForAssertionToPass(
            Runnable function,
            long retries,
            long retryDelayMs
    ) {
        Objects.requireNonNull(function);
        try {
            function.run();
        } catch (FrameworkAssertionException e) {
            if (retries <= 1) throw e;
            Utils.hardSleep(retryDelayMs);
            waitForAssertionToPass(function, (retries - 1), retryDelayMs);
        }
    }
}