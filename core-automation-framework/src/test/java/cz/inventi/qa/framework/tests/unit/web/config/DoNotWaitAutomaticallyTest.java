package cz.inventi.qa.framework.tests.unit.web.config;

import cz.inventi.qa.framework.tests.core.CustomConfigTest;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DoNotWaitAutomaticallyTest extends CustomConfigTest {

    @Test(expectedExceptions = NoSuchElementException.class)
    public void doNotWaitAutomaticallyTest () {
        Assert.assertFalse(getConfigManager().getWebDriverConfigData().getGeneralSettings().getWait().waitsAutomatically());


            .getMenu()
            .clickWhatWeDo()
            .clickAppendContentWithoutWait()
            .clickRefreshAppendedContent();
    }

}

