package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

public class LoadingEventsTest extends RegressionTest {
    private MenuLink EVENTS = MenuLink.EVENTS;
    private Language CZ = Language.CS_CZ;

    @Test
    public void evenetsPageIsLoaded() {
        homePage.getTopPanel()
                .switchLanguageTo(CZ)
                .selectlinkInMainMenu(EVENTS);
    }
}
