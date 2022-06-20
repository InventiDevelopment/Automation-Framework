package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
@FindElement(xpath = "//body")
public class HomePage extends BasePage<HomePage> {

    @FindElement(xpath = "//div[contains(@class, 'toolTipImage')]")
    WebElement<HomePage> tooltipImg;
    @FindElement(xpath = "//div[contains(@class, 'scrollable')]")
    WebElement<HomePage> scrollableElement;
    @FindElement(xpath = "//span[contains(@class, 'toolTipText')]")
    WebElement<HomePage> toolTipText;

    public HomePage(WOProps props) {
        super(props);
    }
}
