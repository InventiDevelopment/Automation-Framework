package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

public class ChangeLanguageTest extends RegressionTest {
    private Language CZ = Language.CS_CZ;
    private Language EN = Language.EN;

    @Test
    public void changeLanguage() {
        homePage
                .switchLanguageTo(CZ)
                .assertCurrentLanguageIs(CZ)
                .switchLanguageTo(EN)
                .assertCurrentLanguageIs(EN);
    }
}
