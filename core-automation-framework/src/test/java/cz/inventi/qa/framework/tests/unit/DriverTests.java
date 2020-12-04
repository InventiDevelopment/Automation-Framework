package cz.inventi.qa.framework.tests.unit;

import cz.inventi.qa.framework.tests.core.TestCase;
import cz.inventi.qa.framework.core.data.enums.Browser;
import cz.inventi.qa.framework.core.managers.ParametersManager;
import org.testng.annotations.Test;

public class DriverTests extends TestCase {

    @Test
    public void testChromeInit() {
        ParametersManager.setBrowser(Browser.CHROME);
    }

}
