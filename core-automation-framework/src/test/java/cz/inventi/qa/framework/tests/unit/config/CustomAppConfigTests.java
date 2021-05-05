package cz.inventi.qa.framework.tests.unit.config;

import cz.inventi.qa.framework.tests.core.CustomConfigTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomAppConfigTests extends CustomConfigTest {

    @Test
    public void isCustomConfigApplied () {
        Assert.assertTrue(getConfigManager().getCustomAppConfigPath().contains("config/customAppsConfig.yml"));
        Assert.assertTrue(getConfigManager().getCustomWebDriverConfigPath().contains("config/customWebDriverConfig.yml"));
    }
}
