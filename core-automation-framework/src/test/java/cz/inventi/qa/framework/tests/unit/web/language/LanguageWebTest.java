package cz.inventi.qa.framework.tests.unit.web.language;

import cz.inventi.qa.framework.tests.core.WebTestCase;
import cz.inventi.qa.framework.core.managers.ParametersManager;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.managers.LanguageManager;
import cz.inventi.qa.framework.testweb.lang.Index;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LanguageWebTest extends WebTestCase {

    @Test
    public void getLanguageDictionaryTest() {
        ParametersManager.getCommonParameters().setLanguage(Language.EN);
        Assert.assertTrue(LanguageManager.getDictionary().keySet().size() > 0);
    }

    @Test
    public void wordTranslationTest() {
        homePage
            .getSidePanel()
            .getSideInfos()
            .get(0)
            .assertReadMoreText(LanguageManager.getTranslation(Index.READ_MORE));
    }
}

