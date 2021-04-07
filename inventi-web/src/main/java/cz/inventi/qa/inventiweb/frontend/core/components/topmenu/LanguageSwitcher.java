package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.inventiweb.frontend.core.data.enums.Keyword;
import lombok.Getter;
import org.testng.Assert;

import java.util.List;

@Getter
@FindElement(xpath = "//div[contains(@class, 'menu__misc')]//nav[contains(@class, 'language-switcher')]")
public class LanguageSwitcher<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//li[contains(@class, 'is-active')]/a[contains(@class, 'language-switcher__item')]/abbr")
    private WebElement currentLanguageWrapper;
    @FindElement(xpath = "//li[not(contains(@class, 'is-active'))]")
    private List<WebElement> inactiveLanguagesListItems;

    public LanguageSwitcher(WOProps props) {
        super(props);
    }

    public LanguageSwitcher<T> switchLanguageTo(Language language) {
        if (!language.equals(getCurrentLanguage())) {
            for (WebElement inactiveLanguageListItem : inactiveLanguagesListItems) {
                inactiveLanguageListItem.click();
            }
        }
        return this;
    }
    
    public Language getCurrentLanguage() {
        String languageName = currentLanguageWrapper
                .getAttribute("title")
                .toLowerCase();

        if (languageName.equals(getLanguageManager().getTranslation(Keyword.CZECH).toLowerCase())) {
            return Language.CS_CZ;
        }
        if (languageName.equals(getLanguageManager().getTranslation(Keyword.ENGLISH).toLowerCase())) {
            return Language.EN;
        }
        throw new RuntimeException("Cannot detect page language from value '" + languageName + "'.");
    }

    public LanguageSwitcher<T> assertCurrentLanguageIs(Language language) {
        Assert.assertEquals(getCurrentLanguage(), language);
        return this;
    }
}
