package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import org.testng.Assert;

@FindElement(xpath = "//nav[contains(@class, 'language-switcher')]")
public class LanguageSwitcher <T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//li[not(contains(@class, 'is-active'))]")
    WebElement currentLanguageListItem;

    public LanguageSwitcher(WOProps props) {
        super(props);
    }

    public LanguageSwitcher switchLanguageTo(Language language) {
        while (!language.equals(getCurrentLanguage())) {
            currentLanguageListItem.click();
        }
        return this;
    }

    public Language getCurrentLanguage() {
        String languageName = currentLanguageListItem.findElement("//a[contains(@class, 'language-switcher__item')]//abbr").getAttribute("title");

        switch (languageName) {
            case "český":
                return Language.CS_CZ;
            case "english":
                return Language.EN;
            default:
                throw new RuntimeException("Cannot detect page language.");
        }
    }

    public LanguageSwitcher assertCurrentLanguageIs(Language language) {
        Assert.assertEquals(getCurrentLanguage(), language);
        return this;
    }
}
