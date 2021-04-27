package cz.inventi.qa.framework.core.objects.parameters;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.data.enums.web.Browser;
import cz.inventi.qa.framework.core.objects.web.WebPage;

public class WebAppParameters <T extends WebPage> {
    private Browser browser;
    private Class<T> startingWebpage;

    public WebAppParameters(String environment, String browser, String language, Class<T> startingWebpage) {
        this.startingWebpage = startingWebpage;
        setBrowser(browser);
    }

    public void setBrowser(String browser) {
        try {
            this.browser = Browser.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            Log.fail("The '" + browser + "' browser is not supported.");
        }
    }

    public Class<T> getStartingWebpage() {
        return startingWebpage;
    }

    public Browser getBrowser() {
        return browser;
    }
}
