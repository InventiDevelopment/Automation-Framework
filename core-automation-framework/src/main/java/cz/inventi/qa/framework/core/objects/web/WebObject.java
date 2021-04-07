package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.WebUtils;
import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.annotations.web.handlers.FindElementHandler;
import cz.inventi.qa.framework.core.factories.web.PageBuilder;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.managers.LanguageManager;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import org.openqa.selenium.WebDriver;

public abstract class WebObject {
    private final WOProps props;

    public WebObject(WOProps props) {
        this.props = props;
        PageBuilder.initElements(this, props);
    }

    public String getXpath() {
        return props.getXpath();
    }

    public WOProps getProps() {
        return props;
    }

    public FindElement getFindElementAnnotation () {
        FindElement findElement = getClass().getAnnotation(FindElement.class);

        if (findElement == null) {
            return new FindElementHandler("", 0);
        }
        return getClass().getAnnotation(FindElement.class);
    }

    public <T extends WebPage> T initPage(Class<T> webPageClass) {
        return WebObjectFactory.initPage(webPageClass, getProps().getAppInstance());
    }

    public WebUtils webUtils() {
        return getAppInstance().getWebUtils();
    }

    public WebDriver getDriver() {
        return getAppInstance().getWebDriverManager().getDriver();
    }

    public AppInstance getAppInstance() {
        return props.getAppInstance();
    }

    public LanguageManager getLanguageManager() {
        return getAppInstance().getLanguageManager();
    }
}
