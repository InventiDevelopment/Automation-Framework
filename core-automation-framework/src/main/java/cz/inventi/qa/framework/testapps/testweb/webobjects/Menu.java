package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
@FindElement(xpath = "//menu")
public class Menu<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "/ul/li/a")
    List<WebElement<Menu<T>>> menuLinks;

    public Menu(WOProps props) {
        super(props);
    }

    public WebElement<Menu<T>> getMenuLink (String name) {
        for (WebElement<Menu<T>> menuLink : menuLinks) {
            if (menuLink.getText().equalsIgnoreCase(name)) {
                return menuLink;
            }
        }
        throw new FrameworkException("Menu link not found.");
    }

    public HomePage clickHome () {
        getMenuLink("home").click();
        return navigateTo(HomePage.class);
    }

    public WhatWeDoPage clickWhatWeDo () {
        getMenuLink("what we do").click();
        return navigateTo(WhatWeDoPage.class);
    }

    public ContactUsPage clickContactUs () {
        getMenuLink("contact us").click();
        return navigateTo(ContactUsPage.class);
    }
}
