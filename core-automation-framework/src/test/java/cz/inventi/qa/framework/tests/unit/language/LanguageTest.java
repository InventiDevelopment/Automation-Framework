package cz.inventi.qa.framework.tests.unit.language;

import cz.inventi.qa.framework.tests.core.TestCase;
import cz.inventi.qa.framework.core.managers.ParametersManager;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.managers.LanguageManager;
import cz.inventi.qa.framework.testweb.lang.Index;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LanguageTest extends TestCase {

    @Test
    public void getLanguageDictionaryTest() {
        ParametersManager.setLanguage(Language.EN);
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

