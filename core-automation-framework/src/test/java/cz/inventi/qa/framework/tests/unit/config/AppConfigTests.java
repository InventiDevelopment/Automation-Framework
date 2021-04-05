package cz.inventi.qa.framework.tests.unit.config;

import cz.inventi.qa.framework.core.data.web.GeneralSettings;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.tests.core.WebTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AppConfigTests extends WebTestCase {

    public GeneralSettings getGeneralSettings () {
        return ConfigManager.getWebDriverConfigData().getGeneralSettings();
    }

    @Test
    public void loadYamlFileTest() {
        ConfigManager.initWebConfigs();
    }

    @Test
    public void customTargetPathTest() {
        ConfigManager.initWebConfigs();
        Assert.assertNotNull(getGeneralSettings().getCustomTargetPath());
    }

    @Test
    public void waitSettingsTest() {
        ConfigManager.initWebConfigs();
        Assert.assertNotNull(getGeneralSettings().getWait().waitsAutomatically());
        Assert.assertNotNull(getGeneralSettings().getWait().getTimeouts().getMin());
        Assert.assertNotNull(getGeneralSettings().getWait().getTimeouts().getMid());
        Assert.assertNotNull(getGeneralSettings().getWait().getTimeouts().getMax());
    }
}

