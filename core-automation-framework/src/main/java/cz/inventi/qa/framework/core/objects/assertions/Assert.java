package cz.inventi.qa.framework.core.objects.assertions;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Optional;

public class Assert {

    public static void assertEquals(Object object1, Object object2, String name) {
        assertTrue(object1.equals(object2), name);
    }

    public static void assertNotEquals(Object object1, Object object2, String name) {
        assertTrue(!object1.equals(object2), name);
    }

    public static void assertTrue(boolean condition, String name) {
        Optional<String> parentStep = Allure.getLifecycle().getCurrentTestCaseOrStep();
        StepResult stepResult = new StepResult();
        stepResult.setName(name);
        Allure.getLifecycle().startStep(parentStep.orElseThrow(), RandomStringUtils.randomAlphanumeric(120), stepResult);

        try {
            org.testng.Assert.assertTrue(condition);
            stepResult.setStatus(Status.PASSED);
            Allure.getLifecycle().stopStep();
        } catch (Exception e) {
            stepResult.setStatus(Status.FAILED);
            Allure.getLifecycle().stopStep();
            throw new RuntimeException(e);
        }
    }

    public static void assertFalse(boolean condition, String name) {
        assertTrue(!condition, name);
    }

    public static void assertNotNull(Object object, String name) {
        assertTrue(object != null, name);
    }

    public static void assertNull(Object object, String name) {
        assertTrue(object == null, name);
    }
}


