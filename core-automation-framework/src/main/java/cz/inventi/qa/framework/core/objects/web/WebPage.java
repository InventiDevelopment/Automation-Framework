package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.utils.WebUtils;
import org.testng.Assert;

public abstract class WebPage extends WebObject {

    public WebPage(WOProps props) {
        super(props);
        waitUntilPageLoaded();
    }

    public void waitUntilPageLoaded() {
        if (getAppInstance().getConfigManager().getWebDriverConfigData().waitsAutomatically()) {
            WebUtils.waitUntilDocumentReady(getAppInstance());
        }
    }

    public String getPageTitle() {
        return getDriver().getTitle();
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
