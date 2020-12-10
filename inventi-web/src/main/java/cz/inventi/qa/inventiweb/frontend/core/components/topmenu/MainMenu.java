package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.factories.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import lombok.Getter;

import java.util.List;

@Getter
@FindElement(xpath = "//div[contains(@class, 'menu__wrapper')]")
public class MainMenu<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//nav[@id='menu-main']//ul//li//a//span")
    private List<WebElement> menuLinks;

    public MainMenu(WOProps props) {
        super(props);
    }

    public T clickMenuItem(MenuLink menuLink) {
        for (WebElement link : menuLinks) {
            String currentLinkText = link.getText().toLowerCase();
            String menuLinkText = menuLink.getText().toLowerCase();
            if (currentLinkText.equals(menuLinkText)) {
                link.click();
                break;
            }
        }

        // TODO create and add all page return values
        switch (menuLink) {
            case EVENTS:
                return null;
            case CAREERS:
                return null;
            case CONTACTS:
                return null;
            case WHAT_WE_DO:
                return null;
            case WHO_ARE_WE:
                return null;
            case CASE_STUDIES:
                return (T) WebObjectFactory.initPage(null);
            default:
                throw new RuntimeException("Given page (" + menuLink.toString() + ") return value not defined.");
        }
    }
}
