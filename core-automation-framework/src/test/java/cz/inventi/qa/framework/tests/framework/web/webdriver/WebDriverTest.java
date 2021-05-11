package cz.inventi.qa.framework.tests.framework.web.webdriver;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.WebDriverTestSteps;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WebDriverTest extends TestBase {
    private WebDriverTestSteps webDriverTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        webDriverTestSteps = new WebDriverTestSteps();
    }

    @Test
    public void checkDriverInitialized() {
        webDriverTestSteps.checkWebDriverInitialized();
    }
}
