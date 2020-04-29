package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.*;
import lombok.Getter;

@Getter
@FindElement(xpath = "//aside")
public class SidePanel<T extends WebObject> extends WebComponent<T> {
    WebComponentList<SideInfo<SidePanel<T>>> sideInfos;

    @FindElement(xpath = "/h2")
    WebElement panelTitle;

    public SidePanel(WOProps props) {
        super(props);
        sideInfos = new WebComponentList(SideInfo.class, this);
    }

}
