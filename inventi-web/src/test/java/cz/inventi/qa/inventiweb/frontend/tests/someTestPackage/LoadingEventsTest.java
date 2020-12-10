package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

import static cz.inventi.qa.framework.core.data.enums.Language.EN;
import static cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink.EVENTS;

public class LoadingEventsTest extends RegressionTest {

    @Test
    public void evenetsPageIsLoaded() {
        homePage.switchLanguageTo(EN)
                .getTopPanel()
                .clickMenuItem(EVENTS);
    }
}
