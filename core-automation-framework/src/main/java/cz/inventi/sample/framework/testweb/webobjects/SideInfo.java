package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebComponent;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import cz.inventi.sample.framework.core.objects.web.WebObject;
import lombok.Getter;
import org.testng.Assert;

@Getter
@FindElement(xpath = "//div[contains(@class, 'sideInfo')]")
public class SideInfo<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "/h3")
    WebElement infoTitle;
    @FindElement(xpath = "/p")
    WebElement infoText;
    @FindElement(xpath = "/span/a")
    WebElement readMoreLink;

    public SideInfo(WOProps props) {
        super(props);
    }

    public SideInfo<T> assertReadMoreText (String expectedText) {
        Assert.assertEquals(readMoreLink.getText(), expectedText);
        return this;
    }

}
