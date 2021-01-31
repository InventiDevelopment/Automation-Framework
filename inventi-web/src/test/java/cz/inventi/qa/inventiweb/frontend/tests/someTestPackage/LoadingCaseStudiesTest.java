package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.CaseStudiesPage;
import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

import static cz.inventi.qa.framework.core.data.enums.Language.EN;

public class LoadingCaseStudiesTest extends RegressionTest {

    @Test
    public void caseStudiesPageIsLoaded() {
        homePage.switchLanguageTo(EN)
                .getTopPanel()
                .clickMenuItem(MenuLink.CASE_STUDIES)
                .assertPageTitle(CaseStudiesPage.expectedTitle);
    }


}
