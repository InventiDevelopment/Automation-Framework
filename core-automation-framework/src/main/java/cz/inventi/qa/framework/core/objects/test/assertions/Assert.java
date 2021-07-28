package cz.inventi.qa.framework.core.objects.test.assertions;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

/**
 * Decorator for TestNG assert so that steps can be recorded
 * in the Allure report.
 */
public class Assert {

    public static void assertEquals(Object actual, Object expected, String name) {
        try {
            org.testng.Assert.assertEquals(actual, expected);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkException(e);
        }
    }

    public static void assertNotEquals(Object actual, Object expected, String name) {
        try {
            org.testng.Assert.assertNotEquals(actual, expected);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkException(e);
        }
    }

    public static void assertTrue(boolean condition, String name) {
        try {
            org.testng.Assert.assertTrue(condition, name);
            createStepWithStatus(name,  Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkException(e);
        }
    }

    public static void assertFalse(boolean condition, String name) {
        try {
            org.testng.Assert.assertFalse(condition);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkException(e);
        }
    }

    public static void assertNotNull(Object object, String name) {
        try {
            org.testng.Assert.assertNotNull(object);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkException(e);
        }
    }

    public static void assertNull(Object object, String name) {
        try {
            org.testng.Assert.assertNull(object);
            createStepWithStatus(name, Status.PASSED);
        } catch (AssertionError e) {
            createStepWithStatus(name, Status.FAILED);
            throw new FrameworkException(e);
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            String serializedObject1 = mapper.writeValueAsString(actual);
            String serializedObject2 = mapper.writeValueAsString(expected);
            assertEquals(serializedObject1, serializedObject2, name);
        } catch (Exception e) {
            throw new FrameworkException("Could not serialize objects to JSON for comparison", e);
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
        Allure.getLifecycle().startStep(parentStep.orElseThrow(), RandomStringUtils.randomAlphanumeric(120), stepResult);
        stepResult.setStatus(status);
        Allure.getLifecycle().stopStep();
        return stepResult;
    }
}