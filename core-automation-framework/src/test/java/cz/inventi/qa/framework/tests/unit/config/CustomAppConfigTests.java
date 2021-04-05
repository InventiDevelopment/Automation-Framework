package cz.inventi.qa.framework.tests.unit.config;

import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.tests.core.CustomConfigTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomAppConfigTests extends CustomConfigTestCase {

    @Test
    public void isCustomConfigApplied () {
        Assert.assertTrue(ConfigManager.getCustomAppConfigPath().contains("config/customAppsConfig.yml"));
        Assert.assertTrue(ConfigManager.getCustomWebDriverConfigPath().contains("config/customWebDriverConfig.yml"));
    }
}
