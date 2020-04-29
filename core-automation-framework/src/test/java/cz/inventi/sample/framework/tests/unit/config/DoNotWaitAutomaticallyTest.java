package cz.inventi.sample.framework.tests.unit.config;

import cz.inventi.sample.framework.tests.core.CustomConfigTestCase;
import cz.inventi.sample.framework.core.managers.ConfigManager;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DoNotWaitAutomaticallyTest extends CustomConfigTestCase {

    @Test(expectedExceptions = NoSuchElementException.class)
    public void doNotWaitAutomaticallyTest () {
        Assert.assertFalse(ConfigManager.getDriverConfigData().getGeneralSettings().getWait().waitsAutomatically());

        homePage
            .getMenu()
            .clickWhatWeDo()
            .clickAppendContentWithoutWait()
            .clickRefreshAppendedContent();
    }

}

