package cz.inventi.qa.framework.core.objects.test.assertions;

import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Decorator for TestNG assert so that steps can be recorded
 * in the Allure report.
 */
public class Assert {

    public static void assertEquals(Object object1, Object object2, String name) {
        try {
            org.testng.Assert.assertEquals(object1, object2);
            createStepWithStatus(name, Status.PASSED);
        } catch (Exception e) {
            createStepWithStatus(name, Status.FAILED);
            throw new RuntimeException(e);
        }
    }

    public static void assertNotEquals(Object object1, Object object2, String name) {
        try {
            org.testng.Assert.assertNotEquals(object1, object2);
            createStepWithStatus(name, Status.PASSED);
        } catch (Exception e) {
            createStepWithStatus(name, Status.FAILED);
            throw new RuntimeException(e);
        }
    }

    public static void assertTrue(boolean condition, String name) {
        try {
            org.testng.Assert.assertTrue(condition);
            createStepWithStatus(name,  Status.PASSED);
        } catch (Exception e) {
            createStepWithStatus(name, Status.FAILED);
            throw new RuntimeException(e);
        }
    }

    public static void assertFalse(boolean condition, String name) {
        try {
            org.testng.Assert.assertFalse(condition);
            createStepWithStatus(name, Status.PASSED);
        } catch (Exception e) {
            createStepWithStatus(name, Status.FAILED);
            throw new RuntimeException(e);
        }
    }

    public static void assertNotNull(Object object, String name) {
        try {
            org.testng.Assert.assertNotNull(object);
            createStepWithStatus(name, Status.PASSED);
        } catch (Exception e) {
            createStepWithStatus(name, Status.FAILED);
            throw new RuntimeException(e);
        }
    }

    public static void assertNull(Object object, String name) {
        try {
            org.testng.Assert.assertNull(object);
            createStepWithStatus(name, Status.PASSED);
        } catch (Exception e) {
            createStepWithStatus(name, Status.FAILED);
            throw new RuntimeException(e);
        }
    }

    private static StepResult createStepWithStatus(String stepName, Status status, Object... comparedItems) {
        Optional<String> parentStep = Allure.getLifecycle().getCurrentTestCaseOrStep();
        StepResult stepResult = new StepResult();
        stepResult.setName(stepName);
        Allure.getLifecycle().startStep(parentStep.orElseThrow(), RandomStringUtils.randomAlphanumeric(120), stepResult);
        stepResult.setStatus(status);
        Allure.getLifecycle().stopStep();
        return stepResult;
    }
}

