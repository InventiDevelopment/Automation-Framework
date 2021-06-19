package cz.inventi.qa.framework.tests.framework.logger;

import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.test.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Framework Unit Tests")
@Story("Test Logging Possibilities")
public class LoggerTests extends TestBase {

    @BeforeClass
    @Override
    public void initSteps() {
    }

    @Test
    public void testInfo() {
        Log.info("Info Test");
    }

    @Test
    public void testWarn() {
        Log.warn("Warn test");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testFailTextException() {
        throw new FrameworkException("Test fail with text and exception", new RuntimeException("Test RuntimeException"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testTestFailException() {
       throw new FrameworkException(new RuntimeException("Test RuntimeException"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testFailText() {
       throw new FrameworkException("Test fail with text");
    }

    @Test
    public void testDebug() {
        Log.debug("Test debug");
    }
}
