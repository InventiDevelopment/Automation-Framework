package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.data.enums.web.WebMandatoryParameters;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.managers.WebDriverManager;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;

public class WebAppInstance<T extends WebPage> extends AppInstance<T> {
    private WebDriverManager webDriverManager;

    public WebAppInstance(ApplicationType applicationType, String applicationName) {
        super(applicationType, applicationName);
        checkMandatoryParametersAreSet(WebMandatoryParameters.class);
        webDriverManager = new WebDriverManager(this);
    }

    public T retrieveOrInitWebPage(Class<T> startingWebPage) {
        if (getApplicationStartingClass() == null) {
            webDriverManager = new WebDriverManager(this);
            webDriverManager.init();
            setApplicationStartingClass(WebObjectFactory.initPage(startingWebPage, this));
        }
        return getApplicationStartingClass();
    }

    public WebDriverManager getWebDriverManager() {
        return webDriverManager;
    }

    /**
     * Ends all running state-dependent instances.
     */
    public void quit() {
        quitWebDriver();
    }

    private void quitWebDriver() {
        if (ApplicationType.WEB.equals(getApplicationType())) {
            webDriverManager.getDriver().quit();
        }
    }
}