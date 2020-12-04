package cz.inventi.qa.framework.tests.unit.config;

import cz.inventi.qa.framework.tests.core.TestCase;
import cz.inventi.qa.framework.core.managers.ConfigManager;
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

