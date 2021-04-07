package cz.inventi.qa.framework.tests.unit.config;

import cz.inventi.qa.framework.tests.core.CustomConfigTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomAppConfigTests extends CustomConfigTestCase {

    @Test
    public void isCustomConfigApplied () {
        Assert.assertTrue(getConfigManager().getCustomAppConfigPath().contains("config/customAppsConfig.yml"));
        Assert.assertTrue(getConfigManager().getCustomWebDriverConfigPath().contains("config/customWebDriverConfig.yml"));
    }
}
