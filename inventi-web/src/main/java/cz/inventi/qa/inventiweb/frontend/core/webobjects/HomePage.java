package cz.inventi.qa.inventiweb.frontend.core.webobjects;


import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.inventiweb.frontend.core.components.footer.FooterMenu;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;
import cz.inventi.qa.inventiweb.frontend.core.data.enums.MenuLink;
import lombok.Getter;

@Getter
@Application("inventiweb")
@FindElement(xpath = "//body")
public class HomePage extends BasePage<HomePage> {
    private FooterMenu<HomePage> footerMenu;
    private TopPanel<HomePage> topPanel;

    public HomePage(WOProps props) {
        super(props);
    }

    public HomePage homePageIsDisplayed() {
        topPanel.logoImg();
        footerMenu.footerMenuIsDisplayed();
        return this;
    }

    public WhoWeArePage navigateToWhoWeArePage() {
        return topPanel.clickMenuItem(MenuLink.WHO_WE_ARE);
    }

    public WhatWeDoPage navigateToWhatWeDoPage() {
        return topPanel.clickMenuItem(MenuLink.WHAT_WE_DO);
    }

    public CaseStudiesPage navigateToCaseStudiesPage() {
        return topPanel.clickMenuItem(MenuLink.CASE_STUDIES);
    }

    public EventsPage navigateToEventsPage() {
        return topPanel.clickMenuItem(MenuLink.EVENTS);
    }

    public CareersPage navigateToCareersPage() {
        return topPanel.clickMenuItem(MenuLink.CAREERS);
    }

    public ContactsPage navigateToContactsPage() {
        return topPanel.clickMenuItem(MenuLink.CAREERS);
    }
}
