package cz.inventi.sample.framework.tests.unit;

import cz.inventi.sample.framework.core.data.enums.Browser;
import cz.inventi.sample.framework.core.managers.ParametersManager;
import cz.inventi.sample.framework.tests.core.TestCase;
import org.testng.annotations.Test;

public class DriverTests extends TestCase {

    @Test
    public void testChromeInit() {
        ParametersManager.setBrowser(Browser.CHROME);
    }

}
