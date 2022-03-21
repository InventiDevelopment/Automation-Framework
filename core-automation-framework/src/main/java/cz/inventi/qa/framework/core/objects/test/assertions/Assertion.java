package cz.inventi.qa.framework.core.objects.test.assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import cz.inventi.qa.framework.core.data.enums.test.AssertionType;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.framework.FrameworkAssertionException;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.utils.ApiUtils;
import cz.inventi.qa.framework.core.utils.Utils;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;


public class Assertion {

    /**
     * Compares all fields of given objects, except
     * the ones defined to skip using AssertJ.
     *
     * @param actual        current version of object
     * @param expected      expected version of object
     * @param name          description for the assertion
     * @param assertionType assertion type
     * @param skipFields    fields to skip
     */
    public static <T> void compareObjectFields(
            T actual,
            T expected,
            String name,
            AssertionType assertionType,
            String... skipFields
    ) {
        try {
            Assertions
                    .assertThat(actual)
                    .usingRecursiveComparison()
                    .ignoringFields(skipFields)
                    .isEqualTo(expected);
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertEqualsRegex(
            Pattern regex,
            String actual,
            String name,
            AssertionType assertionType
    ) {
        try {
            if (regex.matcher(actual).find()) {
                createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
            } else {
                createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
                handleAssertionException(
                        new AssertionError(
                                "Expected:\n" + actual + "\n\nto match regex: '" + regex.pattern() + "'"
                        ),
                        name,
                        assertionType
                );
            }
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertNotEqualsRegex(
            Pattern regex,
            String actual,
            String name,
            AssertionType assertionType
    ) {
        if (!regex.matcher(actual).find()) {
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } else {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(
                    new AssertionError(
                            "Expected:\n" + actual + "\n\nto not match regex: '" + regex.pattern() + "'"
                    ),
                    name,
                    assertionType
            );
        }
    }

    public static void assertEqualsIgnoreCase(
            String actual,
            String expected,
            String name,
            AssertionType assertionType
    ) {
        try {
            Assertions.assertThat(actual).isEqualToIgnoringCase(expected);
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertEquals(
            Object actual,
            Object expected,
            String name,
            AssertionType assertionType
    ) {
        try {
            Assertions.assertThat(actual).isEqualTo(expected);
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertEqualsPrimitive(
            Object actual,
            Object expected,
            String name,
            AssertionType assertionType
    ) {
        if (actual == expected) {
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } else {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException("Expected '" + actual + "' to be '" + expected + "'", name, assertionType);
        }
    }

    public static void assertNotEquals(
            Object actual,
            Object notEqualTo,
            String name,
            AssertionType assertionType
    ) {
        try {
            Assertions.assertThat(actual).isNotEqualTo(notEqualTo);
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertNotEqualsPrimitive(
            Object actual,
            Object notEqualTo,
            String name,
            AssertionType assertionType
    ) {
        if (actual != notEqualTo) {
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } else {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            throw new FrameworkAssertionException(
                    "Expected '" + actual + "' to not be '" + notEqualTo + "'",
                    name
            );
        }
    }

    public static void assertTrue(
            boolean condition,
            String name,
            AssertionType assertionType
    ) {
        try {
            Assertions.assertThat(condition).withFailMessage(name).isTrue();
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertFalse(
            boolean condition,
            String name,
            AssertionType assertionType
    ) {
        try {
            Assertions.assertThat(condition).withFailMessage(name).isFalse();
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertNotNull(
            Object object,
            String name,
            AssertionType assertionType
    ) {
        try {
            Assertions.assertThat(object).isNotNull();
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    public static void assertNull(
            Object object,
            String name,
            AssertionType assertionType
    ) {
        try {
            Assertions.assertThat(object).isNull();
            createAndFinishStepWithStatus(name, Status.PASSED, assertionType);
        } catch (AssertionError e) {
            createAndFinishStepWithStatus(name, Status.FAILED, assertionType);
            handleAssertionException(e, name, assertionType);
        }
    }

    /**
     * Compares equality of objects in serialized JSON format.
     *
     * @param actual        Dto of the first object
     * @param expected      Dto of the second object
     * @param <T>           Type of the Dto
     * @param assertionType assertion type
     */
    public static <T> void assertJsonEquals(
            T actual,
            T expected,
            AssertionType assertionType
    ) {
        assertJsonEquals(
                actual,
                expected,
                "Compare two JSON objects of '" + actual.getClass() + "'",
                assertionType
        );
    }

    /**
     * Compares equality of objects in serialized JSON format.
     *
     * @param actual        Dto of the first object
     * @param expected      Dto of the second object
     * @param <T>           Type of the Dto
     * @param assertionType assertion type
     */
    public static <T> void assertJsonEquals(
            T actual,
            T expected,
            String name,
            AssertionType assertionType
    ) {
        ObjectMapper mapper = ApiUtils.MAPPER.configure(
                SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                true
        );
        String serializedObject1;
        String serializedObject2;
        try {
            serializedObject1 = mapper.writeValueAsString(actual);
            serializedObject2 = mapper.writeValueAsString(expected);
        } catch (JsonProcessingException e) {
            throw new FrameworkAssertionException("Could not serialize objects to JSON for comparison", e);
        }
        if (serializedObject1 != null && serializedObject2 != null) {
            assertEquals(serializedObject1, serializedObject2, name, assertionType);
        }
    }

    /**
     * Retries given function with assertion for given amount
     * of retries and waits for it to pass.
     *
     * @param function     lambda function containing assertion using FrameworkAssertionException
     * @param retries      number of retries
     * @param retryDelayMs time for delay after retry
     */
    public static void waitForAssertionToPass(
            Runnable function,
            String name,
            long retries,
            long retryDelayMs,
            String stepUuid,
            AssertionType assertionType
    ) {
        Objects.requireNonNull(function);
        if (stepUuid == null) {
            stepUuid = startStep(name, assertionType);
            Log.debug("Waiting for assertion to pass with (" + retries + ") retries");
        }
        try {
            function.run();
            finishStepWithStatus(stepUuid, Status.PASSED);
        } catch (FrameworkAssertionException e) {
            Log.debug("Failed retry (" + retries + ") for assertion");
            if (retries <= 1) {
                if (AssertionType.HARD.equals(assertionType)) throw e;
                finishStepWithStatus(stepUuid, Status.FAILED);
            }
            Utils.hardSleep(retryDelayMs);
            waitForAssertionToPass(function, name, (retries - 1), retryDelayMs, stepUuid, assertionType);
        }
    }

    public static void waitForAssertionToPass(
            Runnable function,
            String name,
            long retries,
            long retryDelayMs,
            AssertionType assertionType
    ) {
        waitForAssertionToPass(function, name, retries, retryDelayMs, null, assertionType);
    }

    /**
     * Compares content of two files.
     *
     * @param file1         first file
     * @param file2         second file
     * @param assertionType assertion type
     */
    public static void assertFileContentEquals(
            File file1,
            File file2,
            String name,
            AssertionType assertionType
    ) {
        try {
            assertTrue(FileUtils.contentEquals(file1, file2), name, assertionType);
        } catch (FrameworkAssertionException e) {
            handleAssertionException(
                    "Given files are not the same (" + file1.getName() + ", " + file2.getName() + ")",
                    name,
                    assertionType
            );
        } catch (IOException e) {
            throw new FrameworkException(
                    "Error occurred when trying to compare files (" + file1.getName() + ", " + file2.getName() + ")",
                    e
            );
        }
    }

    /**
     * Creates step in Allure report for assertion.
     *
     * @param stepName      Name of the step
     * @param status        Status of the step
     * @param comparedItems Items to be compared
     * @param assertionType Type of assertion
     * @return Step UUID
     */
    public static String createAndFinishStepWithStatus(
            String stepName,
            Status status,
            AssertionType assertionType,
            Object... comparedItems
    ) {
        String stepUuid = startStep(stepName, assertionType);
        finishStepWithStatus(stepUuid, status);
        return stepUuid;
    }

    /**
     * Creates Allure StepResult.
     *
     * @param stepName      Name of the step
     * @param assertionType Type of assertion
     * @return Allure StepResult
     */
    private static String startStep(
            String stepName,
            AssertionType assertionType
    ) {
        String stepUuid = RandomStringUtils.randomAlphanumeric(32);
        StepResult stepResult = new StepResult();
        stepResult.setName(stepName + " (" + assertionType.getName() + ")");
        Allure.getLifecycle().startStep(
                Allure.getLifecycle().getCurrentTestCaseOrStep().orElseThrow(),
                stepUuid,
                stepResult
        );
        return stepUuid;
    }

    /**
     * Stops Allure StepResult with given status.
     *
     * @param stepUuid UUID of step
     * @param status   Status of the step
     */
    private static void finishStepWithStatus(
            String stepUuid,
            Status status
    ) {
        Allure.getLifecycle().updateStep(stepUuid, (stepResult -> stepResult.setStatus(status)));
        Allure.getLifecycle().stopStep(stepUuid);
    }

    private static SoftAssertCollector getSoftAssertCollector() {
        return FrameworkManager.getCurrentTestRun().getSoftAssertCollector();
    }

    public static void handleAssertionException(AssertionError e, String name, AssertionType assertionType) {
        handleAssertionException(
                new FrameworkAssertionException(name, e),
                assertionType
        );
    }

    public static void handleAssertionException(String errorMessage, String name, AssertionType assertionType) {
        handleAssertionException(
                new FrameworkAssertionException(errorMessage, name),
                assertionType
        );
    }

    public static void handleAssertionException(
            FrameworkAssertionException frameworkAssertionException,
            AssertionType assertionType
    ) {
        if (AssertionType.SOFT.equals(assertionType)) {
            Allure.addAttachment(
                    frameworkAssertionException.getMessage() + " (soft assertion exception)",
                    frameworkAssertionException.getStackTraceAsString()
            );
            getSoftAssertCollector().addException(frameworkAssertionException);
        } else {
            throw frameworkAssertionException;
        }
    }
}