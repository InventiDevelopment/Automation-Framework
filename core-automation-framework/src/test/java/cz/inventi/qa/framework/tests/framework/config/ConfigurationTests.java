package cz.inventi.qa.framework.tests.framework.config;

import cz.inventi.qa.framework.core.data.enums.web.WindowSizeType;
import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.ConfigManagerTestSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Framework Unit Tests")
@Story("Test Configuration of the Framework")
public class ConfigurationTests extends TestBase {
    private ConfigManagerTestSteps configManagerTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        configManagerTestSteps = new ConfigManagerTestSteps();
    }

    @Test(description = "Check WEB Configuration Was Initialized")
    public void checkWebAppConfigurationInitialized() {
        configManagerTestSteps.checkWebAppConfigurationLoaded("testweb");
    }

    @Test(description = "Check API Configuration Was Initialized")
    public void checkApiAppConfigurationInitialized() {
        configManagerTestSteps.checkApiAppConfigurationLoaded("jsonplaceholder");
    }

    @Test(description = "Check WebDriver Configuration Is Initialized")
    public void checkWebDriverConfigurationIsInitialized() {
        configManagerTestSteps
                .checkWebDriverConfigurationsFileIsLoaded()
                .checkWaitsAutomaticallyValue(true)
                .checkWebDriverMaxTimeoutValue(30000)
                .checkWindowSizeTypeValue(WindowSizeType.MAXIMIZED);
    }

    @Test(description = "Check WebDriver Configuration Is Initialized - Custom Config")
    public void checkWebDriverConfigurationIsInitializedCustomConfig() {
        configManagerTestSteps
                .checkWebDriverConfigurationsFileIsLoaded()
                .checkWaitsAutomaticallyValue(false)
                .checkWindowSizeDimensionsValue(1920, 1080);
    }
}