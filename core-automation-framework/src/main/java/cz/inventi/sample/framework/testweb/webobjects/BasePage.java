package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import cz.inventi.sample.framework.core.objects.web.WebPage;
import lombok.Getter;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Getter
public class BasePage<T extends WebPage> extends WebPage {
    SidePanel<T> sidePanel;
    Footer<T> footer;
    Menu<T> menu;
    Popup<T> popup;

    @FindElement(xpath = "//div[@id='screen-overlay')]")
    WebElement loadingOverlay;
    @FindElement(xpath = "//div[contains(@class, 'articleTitleWrapper')]/h1")
    WebElement articleTitle;

    public BasePage(WOProps props) {
        super(props);
    }

}
