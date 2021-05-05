package cz.inventi.qa.framework.tests.unit.web.config;

import cz.inventi.qa.framework.core.objects.test.WebFlow;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WaitAutomaticallyTest extends WebFlow {

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

