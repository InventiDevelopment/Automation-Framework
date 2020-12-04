package cz.inventi.qa.framework.tests.unit.config;

import cz.inventi.qa.framework.core.data.web.GeneralSettings;
import cz.inventi.qa.framework.tests.core.TestCase;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ConfigTests extends TestCase {

    public GeneralSettings getGeneralSettings () {
        return ConfigManager.getDriverConfigData().getGeneralSettings();
    }

    @Test
    public void loadYamlFileTest() {
        ConfigManager.init();
    }

    @Test
    public void customTargetPathTest() {
        ConfigManager.init();
        Assert.assertNotNull(getGeneralSettings().getCustomTargetPath());
    }

    @Test
    public void waitSettingsTest() {
        ConfigManager.init();
        Assert.assertNotNull(getGeneralSettings().getWait().waitsAutomatically());
        Assert.assertNotNull(getGeneralSettings().getWait().getTimeouts().getMin());
        Assert.assertNotNull(getGeneralSettings().getWait().getTimeouts().getMid());
        Assert.assertNotNull(getGeneralSettings().getWait().getTimeouts().getMax());
    }
}

