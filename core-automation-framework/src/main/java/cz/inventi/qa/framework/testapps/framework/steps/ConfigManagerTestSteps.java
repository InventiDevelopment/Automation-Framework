package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.data.config.AppsConfigData;
import cz.inventi.qa.framework.core.data.config.WebDriverConfigData;
import cz.inventi.qa.framework.core.data.enums.web.WindowSizeType;
import cz.inventi.qa.framework.core.data.web.WindowSizeDimensions;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.core.objects.test.steps.StepsBase;
import cz.inventi.qa.framework.testapps.testapi.JsonPlaceHolderApi;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import io.qameta.allure.Step;

public class ConfigManagerTestSteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);
    private final JsonPlaceHolderApi jsonPlaceHolderApi = getApiAppInstanceOf(JsonPlaceHolderApi.class);
    private final WebDriverConfigData webDriverConfigData = homePage
            .getAppInstance()
            .getConfigManager()
            .getWebDriverConfigData();
    private final AppsConfigData appsConfigData = jsonPlaceHolderApi
            .getAppInstance()
            .getConfigManager()
            .getAppsConfigData();

    @Step("Check ConfigManager Has Been Initialized")
    public ConfigManagerTestSteps checkAppConfigManagerInitialized() {
        ConfigManager webConfigManager = homePage.getAppInstance().getConfigManager();
        ConfigManager apiConfigManager = jsonPlaceHolderApi.getAppInstance().getConfigManager();
        Assert.assertNotNull(webConfigManager, "Web ConfigManager object is not null");
        Assert.assertNotNull(apiConfigManager, "Api ConfigManager object is not null");
        return this;
    }

    @Step("Check Application Configuration Files Are Loaded")
    public ConfigManagerTestSteps checkAppConfigurationFilesAreLoaded() {
        AppsConfigData webAppConfigData = homePage
                .getAppInstance()
                .getConfigManager()
                .getAppsConfigData();
        AppsConfigData apiAppConfigData = jsonPlaceHolderApi
                .getAppInstance()
                .getConfigManager()
                .getAppsConfigData();
        Assert.assertNotNull(apiAppConfigData.getApplications(),
                "Application object for API app is not null");
        Assert.assertNotNull(webAppConfigData.getApplications(),
                "Application object for WEB app is not null");
        Assert.assertNotEquals(webAppConfigData,
                apiAppConfigData,
                "Application configuration files are not shared for WEB and API application instance");
        return this;
    }

    @Step("Check Application Configuration Contains Applications ({applicationName})")
    public ConfigManagerTestSteps checkApplicationConfigurationContainsWebApp(String applicationName) {
        Assert.assertTrue(
                appsConfigData.getApplications().getWeb().containsKey(applicationName),
                "Application '" + applicationName + "' found in app config"
        );
        return this;
    }

    @Step("Check Application PROD URL Value for '{appName}' WEB app is '{applicationProdUrl}'")
    public ConfigManagerTestSteps checkFirstWebApplicationProdUrlIs(String appName, String applicationProdUrl) {
        Assert.assertEquals(
                appsConfigData.getApplications().getWeb().get(appName).getEnvironments().get("PROD"),
                applicationProdUrl,
                    "PROD URL is '" + applicationProdUrl + "'"
        );
        return this;
    }

    @Step("Check WebDriver Configuration File Is Loaded")
    public ConfigManagerTestSteps checkWebDriverConfigurationsFileIsLoaded() {
        Assert.assertNotNull(
                webDriverConfigData,
                "WebDriverConfigData object is not null"
        );
        return this;
    }

    @Step("Check WindowSizeType is '{windowSizeType}'")
    public ConfigManagerTestSteps checkWindowSizeTypeValue(WindowSizeType windowSizeType) {
        Assert.assertEquals(
                webDriverConfigData.getGeneralSettings().getWindowSize().getSizeType(),
                WindowSizeType.MAXIMIZED,
                "Window size is set to MAXIMIZED enum"
        );
        return this;
    }

    @Step("Check WindowSize Dimensions Are Set to '{width}'x'{height}'")
    public ConfigManagerTestSteps checkWindowSizeDimensionsValue(int width, int height) {
        WindowSizeDimensions windowSizeDimensions = webDriverConfigData
                .getGeneralSettings()
                .getWindowSize()
                .getDimensions();
        Assert.assertEquals(
                windowSizeDimensions.getWidth(),
                width,
                "Window width is set to " + height
        );
        Assert.assertEquals(
                windowSizeDimensions.getHeight(),
                height,
                "Window height is set to " + height
        );
        return this;
    }

    @Step("Check WebDriver waitsAutomatically Parameter Is '{waitsAutomatically}'")
    public ConfigManagerTestSteps checkWaitsAutomaticallyValue(boolean waitsAutomatically) {
        Assert.assertEquals(
                webDriverConfigData.waitsAutomatically(),
                waitsAutomatically,
                "WaitsAutomatically value is '" + waitsAutomatically + "'"
        );
        return this;
    }

    @Step("Check WebDriver MAX Timeout Is Set To '{maxTimeout}'")
    public ConfigManagerTestSteps checkWebDriverMaxTimeoutValue(int maxTimeout) {
        Assert.assertEquals(
                webDriverConfigData.getTimeouts().getMax(),
                maxTimeout,
                "Max timeout value is '" + maxTimeout + "'"
        );
        return this;
    }
}

