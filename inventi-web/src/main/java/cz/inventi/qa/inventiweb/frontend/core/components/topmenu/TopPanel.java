package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.objects.web.*;
import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.HomePage;
import lombok.Getter;

@Getter
@FindElement(xpath = "//div[@id='header']")
public class TopPanel<T extends WebObject> extends WebComponent<T> {
    LanguageSwitcher<TopPanel<T>> languageSwitcher;
    MainMenu<TopPanel<T>> mainMenu;

    @FindElement(xpath = "//div[contains(@class, 'logo-image')]//img")
    private WebElement logoImg;

    public TopPanel(WOProps props) {
        super(props);
    }

    public TopPanel<T> switchLanguageTo(Language language){
        languageSwitcher.switchLanguageTo(language);
        return this;
    }

    public TopPanel<T> assertCurrentLanguageIs(Language language){
        languageSwitcher.assertCurrentLanguageIs(language);
        return this;
    }

    public HomePage logoImg() {
        logoImg.isDisplayed();
        return WebObjectFactory.initPage(HomePage.class);
    }

    public <W extends WebPage> W clickMenuItem(MenuLink menuLink){
        return mainMenu.clickMenuItem(menuLink);
    }
}
