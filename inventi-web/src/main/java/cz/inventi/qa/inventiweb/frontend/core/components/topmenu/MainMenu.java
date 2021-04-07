package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.*;
import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.*;
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
            String menuLinkText = menuLink.getText(getAppInstance()).toLowerCase();
            if (currentLinkText.equals(menuLinkText)) {
                link.click();
                break;
            }
        }

        switch (menuLink) {
            case EVENTS:
                return (W) initPage(EventsPage.class);
            case CAREERS:
                return (W) initPage(CareersPage.class);
            case CONTACTS:
                return (W) initPage(ContactsPage.class);
            case WHAT_WE_DO:
                return (W) initPage(WhatWeDoPage.class);
            case WHO_WE_ARE:
                return (W) initPage(WhoWeArePage.class);
            case CASE_STUDIES:
                return (W) initPage(CaseStudiesPage.class);
            default:
                throw new RuntimeException("Given page (" + menuLink.toString() + ") return value not defined.");
        }
    }
}
