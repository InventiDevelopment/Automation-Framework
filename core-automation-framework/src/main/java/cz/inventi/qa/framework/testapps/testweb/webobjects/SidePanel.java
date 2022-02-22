package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.*;
import lombok.Getter;

@Getter
@FindElement(xpath = "//aside")
public class SidePanel<T extends WebObject> extends WebComponent<T> {
    WebComponentList<SideInfo<T>> sideInfos;

    @FindElement(xpath = "/h2")
    WebElement<SidePanel<T>> panelTitle;

    public SidePanel(WOProps props) {
        super(props);
        sideInfos = new WebComponentList<>(SideInfo.class, props);
    }
}
