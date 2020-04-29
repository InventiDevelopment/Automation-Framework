package cz.inventi.sample.framework.tests.unit.language;

import cz.inventi.sample.framework.core.managers.ParametersManager;
import cz.inventi.sample.framework.core.data.enums.Language;
import cz.inventi.sample.framework.core.managers.LanguageManager;
import cz.inventi.sample.framework.tests.core.TestCase;
import cz.inventi.sample.framework.testweb.lang.Index;
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

