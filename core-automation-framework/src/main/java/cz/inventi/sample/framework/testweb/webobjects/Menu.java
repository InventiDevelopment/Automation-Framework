package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.factories.webobject.WebObjectFactory;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebComponent;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import cz.inventi.sample.framework.core.objects.web.WebObject;
import lombok.Getter;

import java.util.List;


@Getter
@FindElement(xpath = "//menu")
public class Menu<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "/ul/li/a")
    List<WebElement> menuLinks;

    public Menu(WOProps props) {
        super(props);
    }

    public WebElement getMenuLink (String name) {
        for (WebElement menuLink : menuLinks) {
            if (menuLink.getText().toLowerCase().equals(name.toLowerCase())) {
                return menuLink;
            }
        }
        throw new RuntimeException("Menu link not found.");
    }

    public HomePage clickHome () {
        getMenuLink("home").click();
        return WebObjectFactory.initPage(HomePage.class);
    }

    public WhatWeDoPage clickWhatWeDo () {
        getMenuLink("what we do").click();
        return WebObjectFactory.initPage(WhatWeDoPage.class);
    }

    public ContactUsPage clickContactUs () {
        getMenuLink("contact us").click();
        return WebObjectFactory.initPage(ContactUsPage.class);
    }
}
