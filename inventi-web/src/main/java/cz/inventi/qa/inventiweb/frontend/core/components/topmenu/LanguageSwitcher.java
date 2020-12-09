package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.factories.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.HomePage;
import lombok.Getter;
import org.testng.Assert;

import java.util.List;

@Getter
@FindElement(xpath = "//div[contains(@class, 'menu__misc')]//nav[contains(@class, 'language-switcher')]")
public class LanguageSwitcher <T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//li[contains(@class, 'is-active')]/a[contains(@class, 'language-switcher__item')]/abbr")
    WebElement currentLanguageListItem;

    @FindElement(xpath = "//li[not(contains(@class, 'is-active'))]")
    List<WebElement> inactiveLanguagesListItems;

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
        String languageName = currentLanguageListItem
                                .getAttribute("title")
                                .toLowerCase();

        // TODO remove hard-coded values and use Keyword class and dictionary values comparison instead
        switch (languageName) {
            case "český":
                return Language.CS_CZ;
            case "english":
                return Language.EN;
            default:
                throw new RuntimeException("Cannot detect page language.");
        }
    }

    public LanguageSwitcher<T> assertCurrentLanguageIs(Language language) {
        Assert.assertEquals(getCurrentLanguage(), language);
        return this;
    }
}
