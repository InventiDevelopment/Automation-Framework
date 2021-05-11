package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.test.StepsBase;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.testapps.testweb.lang.Index;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import io.qameta.allure.Step;

public class LanguageTestSteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);

    @Step("Check Current Language Is Set to '{language}'")
    public LanguageTestSteps checkCurrentLanguageIsSetTo(Language language) {
        Assert.assertEquals(homePage.getAppInstance().getLanguageManager().getCurrentLanguage(),
                language,
                "Check current language is " + language);
        return this;
    }

    @Step("Check Language File Is Loaded")
    public LanguageTestSteps checkLanguageFileIsLoaded() {
        Assert.assertTrue(homePage.getAppInstance().getLanguageManager().getDictionary().keySet().size() > 0,
                "There are dictionary phrases loaded");
        return this;
    }

    @Step("Check Translation of '{webPagePhrase}' Is Correct")
    public LanguageTestSteps checkTranslationIsCorrect(Index webPagePhrase, String expectedTranslation) {
        Assert.assertEquals(expectedTranslation.toLowerCase(),
                homePage.getAppInstance().getLanguageManager().getTranslation(webPagePhrase).toLowerCase(),
                "Check translation is '" + expectedTranslation + "'");
        return this;
    }
}

