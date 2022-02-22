package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import lombok.Getter;
import org.testng.Assert;

@Getter
@FindElement(xpath = "//div[contains(@class, 'sideInfo')]")
public class SideInfo<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "/h3")
    WebElement<SideInfo<T>> infoTitle;
    @FindElement(xpath = "/p")
    WebElement<SideInfo<T>> infoText;
    @FindElement(xpath = "/span/a")
    WebElement<SideInfo<T>> readMoreLink;

    public SideInfo(WOProps props) {
        super(props);
    }

    public SideInfo<T> assertReadMoreText (String expectedText) {
        Assert.assertEquals(readMoreLink.getText(), expectedText);
        return this;
    }

}
