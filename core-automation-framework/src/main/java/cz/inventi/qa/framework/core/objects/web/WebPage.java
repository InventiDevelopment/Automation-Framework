package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.Utils;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.DriverManager;
import org.testng.Assert;

public abstract class WebPage extends WebObject {

    public WebPage(WOProps props) {
        super(props);
        waitUntilPageLoaded();
    }

    public void waitUntilPageLoaded() {
        if (ConfigManager.getDriverConfigData().waitsAutomatically()) {
            Utils.waitUntilDocumentReady();
        }
    }

    public String getPageTitle() {
        return DriverManager.getDriver().getTitle();
    }

    public WebPage assertPageTitle(String expectedTitle) {
        Assert.assertEquals(getPageTitle(), expectedTitle);
        return this;
    }

    public <T extends WebPage> T regeneratePage() {
        return (T) this;
    }
}
