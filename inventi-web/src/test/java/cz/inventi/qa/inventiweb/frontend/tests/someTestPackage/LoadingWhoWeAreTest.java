package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.WhoWeArePage;
import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

import static cz.inventi.qa.framework.core.data.enums.Language.EN;

public class LoadingWhoWeAreTest extends RegressionTest {

    @Test
    public void whoWeArePageIsLoaded() {
        homePage.switchLanguageTo(EN)
                .getTopPanel()
                .clickMenuItem(MenuLink.WHO_WE_ARE)
                .assertPageTitle(WhoWeArePage.expectedTitle);
    }
}
