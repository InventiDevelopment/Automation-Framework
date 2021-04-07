package cz.inventi.qa.framework.tests.unit.web.config;

import cz.inventi.qa.framework.tests.core.CustomConfigTestCase;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DoNotWaitAutomaticallyTest extends CustomConfigTestCase {

    @Test(expectedExceptions = NoSuchElementException.class)
    public void doNotWaitAutomaticallyTest () {
        Assert.assertFalse(getConfigManager().getWebDriverConfigData().getGeneralSettings().getWait().waitsAutomatically());

        homePage
            .getMenu()
            .clickWhatWeDo()
            .clickAppendContentWithoutWait()
            .clickRefreshAppendedContent();
    }

}

