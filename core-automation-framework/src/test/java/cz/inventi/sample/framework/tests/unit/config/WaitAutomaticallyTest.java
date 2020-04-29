package cz.inventi.sample.framework.tests.unit.config;

import cz.inventi.sample.framework.core.managers.ConfigManager;
import cz.inventi.sample.framework.tests.core.TestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WaitAutomaticallyTest extends TestCase {

    @Test
    public void waitAutomatically () {
        Assert.assertTrue(ConfigManager.getDriverConfigData().getGeneralSettings().getWait().waitsAutomatically());

        homePage
            .getMenu()
            .clickWhatWeDo()
            .clickAppendContentWithoutWait()
            .clickRefreshAppendedContent();
    }

}

