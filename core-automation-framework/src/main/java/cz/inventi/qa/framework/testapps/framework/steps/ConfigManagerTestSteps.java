package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.data.config.AppConfigData;
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

    @Step("Check Proper API Application Configuration File Is Loaded")
    public void checkApiAppConfigurationLoaded(String apiAppName) {
        ConfigManager apiConfigManager = jsonPlaceHolderApi.getAppInstance().getConfigManager();
        Assert.assertNotNull(
                apiConfigManager,
                "Api ConfigManager object is not null"
        );
        AppConfigData apiAppConfigData = jsonPlaceHolderApi
                .getAppInstance()
                .getConfigManager()
                .getAppsConfigData();
        Assert.assertEquals(
                apiAppConfigData.getApplicationConfig().getApi().size(),
                1,
                "1 API application config was initialized"
        );
        Assert.assertNotNull(
                apiAppConfigData.getApplicationConfig().getApi().get(apiAppName),
                "Application '" + apiAppName + "' found in initialized config files"
        );
    }

    @Step("Check Proper WEB Application Configuration File Is Loaded")
    public void checkWebAppConfigurationLoaded(String webAppName) {
        ConfigManager webConfigManager = homePage.getAppInstance().getConfigManager();
        Assert.assertNotNull(
                webConfigManager,
                "Web ConfigManager object is not null"
        );
        AppConfigData webAppConfigData = homePage
                .getAppInstance()
                .getConfigManager()
                .getAppsConfigData();
        Assert.assertEquals(
                webAppConfigData.getApplicationConfig().getWeb().size(),
                1,
                "1 WEB application config was initialized"
        );
        Assert.assertNotNull(
                webAppConfigData.getApplicationConfig().getWeb().get(webAppName),
                "Application '" + webAppName + "' found in initialized config files"
        );
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

