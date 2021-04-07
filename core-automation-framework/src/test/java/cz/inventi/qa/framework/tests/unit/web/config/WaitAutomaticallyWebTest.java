package cz.inventi.qa.framework.tests.unit.web.config;

import cz.inventi.qa.framework.tests.core.WebTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WaitAutomaticallyWebTest extends WebTestCase {

    @Test
    public void waitAutomatically () {
        Assert.assertTrue(getConfigManager().getWebDriverConfigData().getGeneralSettings().getWait().waitsAutomatically());

        homePage
            .getMenu()
            .clickWhatWeDo()
            .clickAppendContentWithoutWait()
            .clickRefreshAppendedContent();
    }
}

