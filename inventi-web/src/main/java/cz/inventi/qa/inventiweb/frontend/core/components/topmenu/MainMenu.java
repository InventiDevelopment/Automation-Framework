package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.factories.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.objects.web.*;
import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.EventsPage;
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

    public <W extends WebPage> W clickMenuItem(MenuLink menuLink) {
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
                return (W) WebObjectFactory.initPage(EventsPage.class);
            case CAREERS:
                return null;
            case CONTACTS:
                return null;
            case WHAT_WE_DO:
                return null;
            case WHO_ARE_WE:
                return null;
            case CASE_STUDIES:
                return null;
            default:
                throw new RuntimeException("Given page (" + menuLink.toString() + ") return value not defined.");
        }
    }
}
