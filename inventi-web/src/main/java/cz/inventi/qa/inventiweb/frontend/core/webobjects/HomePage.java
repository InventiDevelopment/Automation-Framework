package cz.inventi.qa.inventiweb.frontend.core.webobjects;


import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.testweb.webobjects.BasePage;
import org.testng.Assert;

@Application("inventiweb")
public class HomePage extends BasePage<HomePage> {

    @FindElement(xpath = "//div[contains(@class, 'logo-image')]//img")
    private WebElement logoImg;

    public HomePage(WOProps props) {
        super(props);
    }

    public HomePage assertLogoIsClickable() {
        Assert.assertTrue(logoImg.isClickable());
        logoImg.clickJS();
        return this;
    }
}
