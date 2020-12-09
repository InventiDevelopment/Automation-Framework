package cz.inventi.qa.inventiweb.frontend.core.components.footer;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;

public class FooterMenu<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//div[@id='footer']//div[contains(@class, 'links')]")
    private WebElement footerMenu;

    public FooterMenu(WOProps props) {
        super(props);
    }

    public FooterMenu footerMenuIsDisplay(){
        footerMenu.isDisplayed();
        return this;
    }
}
