package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.Application;
import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
@Application("testweb")
@FindElement(xpath = "//body")
public class HomePage extends BasePage<HomePage> {

    @FindElement(xpath = "//div[contains(@class, 'toolTipImage')]")
    WebElement tooltipImg;
    @FindElement(xpath = "//div[contains(@class, 'scrollable')]")
    WebElement scrollableElement;
    @FindElement(xpath = "//span[contains(@class, 'toolTipText')]")
    WebElement toolTipText;

    public HomePage(WOProps props) {
        super(props);
    }

}
