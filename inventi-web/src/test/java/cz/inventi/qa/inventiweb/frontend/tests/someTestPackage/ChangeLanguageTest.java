package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

import static cz.inventi.qa.framework.core.data.enums.Language.CS_CZ;
import static cz.inventi.qa.framework.core.data.enums.Language.EN;

public class ChangeLanguageTest extends RegressionTest {

    @Test
    public void changeLanguage() {
        homePage.getTopPanel()
                .switchLanguageTo(CS_CZ)
                .assertCurrentLanguageIs(CS_CZ)
                .switchLanguageTo(EN)
                .assertCurrentLanguageIs(EN);
    }
}
