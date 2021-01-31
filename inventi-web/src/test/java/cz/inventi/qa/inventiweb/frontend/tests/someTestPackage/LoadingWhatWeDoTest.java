package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.WhatWeDoPage;
import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

import static cz.inventi.qa.framework.core.data.enums.Language.EN;

public class LoadingWhatWeDoTest extends RegressionTest {

    @Test
    public void whatWeDoPageIsLoaded() {
        homePage.switchLanguageTo(EN)
                .getTopPanel()
                .clickMenuItem(MenuLink.WHAT_WE_DO)
                .assertPageTitle(WhatWeDoPage.expectedTitle);
    }
}
