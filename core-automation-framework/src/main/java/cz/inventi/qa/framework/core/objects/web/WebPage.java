package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.Utils;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.WebDriverManager;
import org.testng.Assert;

public abstract class WebPage extends WebObject {

    public WebPage(WOProps props) {
        super(props);
        waitUntilPageLoaded();
    }

    public void waitUntilPageLoaded() {
        if (ConfigManager.getWebDriverConfigData().waitsAutomatically()) {
            Utils.waitUntilDocumentReady();
        }
    }

    public String getPageTitle() {
        return WebDriverManager.getDriver().getTitle();
    }

    public WebPage assertPageTitle(String expectedTitle) {
        Assert.assertEquals(getPageTitle(), expectedTitle);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends WebPage> T regeneratePage() {
        return (T) this;
    }
}
