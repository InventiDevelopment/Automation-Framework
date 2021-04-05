package cz.inventi.qa.framework.tests.unit.logger;

import cz.inventi.qa.framework.core.Log;
import org.testng.annotations.Test;

public class LoggerTests {

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
        Log.fail("Test fail with text and exception", new RuntimeException("Test RuntimeException"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testTestFailException() {
       Log.fail(new RuntimeException("Test RuntimeException"));
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testFailText() {
       Log.fail("Test fail with text");
    }

    @Test
    public void testDebug() {
        Log.debug("Test debug");
    }
}
