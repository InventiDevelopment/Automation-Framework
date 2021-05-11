package cz.inventi.qa.framework.tests.framework.language;

import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.LanguageTestSteps;
import cz.inventi.qa.framework.testapps.testweb.lang.Index;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LanguageWebTest extends TestBase {
    private LanguageTestSteps languageTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        languageTestSteps = new LanguageTestSteps();
    }

    @Test(description = "Check Current Language")
    @Parameters({ "language" })
    public void checkCurrentLanguage(String language) {
        languageTestSteps.checkCurrentLanguageIsSetTo(Language.valueOf(language));
    }

    @Test(description = "Check Translations Are Working")
    public void checkTranslations() {
        languageTestSteps
                .checkLanguageFileIsLoaded()
                .checkTranslationIsCorrect(Index.READ_MORE, "Read More");
    }
}

