package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import lombok.Getter;

@Getter
@FindElement(xpath = "//div[@id='header']")
public class TopPanel<T extends WebObject> extends WebComponent<T> {
    LanguageSwitcher<TopPanel<T>> languageSwitcher;
   // MainMenu<TopPanel<T>> mainMenu;

    @FindElement(xpath = "//div[contains(@class, 'logo-image')]//img")
    private WebElement logoImg;

    public TopPanel(WOProps props) {
        super(props);
    }
}
