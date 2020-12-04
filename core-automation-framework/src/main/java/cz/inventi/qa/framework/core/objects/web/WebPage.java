package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.managers.DriverManager;
import org.testng.Assert;

public abstract class WebPage extends WebObject {

    public WebPage(WOProps props) {
        super(props);
    }

    public String getPageTitle () {
        return DriverManager.getDriver().getTitle();
    }

    public WebPage assertPageTitle (String expectedTitle) {
        Assert.assertEquals(getPageTitle(), expectedTitle);
        return this;
    }
}
