package cz.inventi.sample.framework.tests.unit.config;

import cz.inventi.sample.framework.core.managers.ConfigManager;
import cz.inventi.sample.framework.tests.core.CustomConfigTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomConfigTests extends CustomConfigTestCase {

    @Test
    public void isCustomConfigApplied () {
        Assert.assertTrue(ConfigManager.getCustomAppConfigPath().contains("config/customAppConfig.yml"));
        Assert.assertTrue(ConfigManager.getCustomDriverConfigPath().contains("config/customDriverConfig.yml"));
    }
}
