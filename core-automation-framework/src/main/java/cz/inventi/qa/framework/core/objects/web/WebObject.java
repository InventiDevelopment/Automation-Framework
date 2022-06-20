package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.annotations.web.handlers.FindElementHandler;
import cz.inventi.qa.framework.core.factories.web.PageBuilder;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.managers.LanguageManager;
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
        if (findElement == null) return new FindElementHandler("", 0);
        return getClass().getAnnotation(FindElement.class);
    }

    /**
     * Create a WebObject instance within given AppInstance.
     * @param webObjectClass class of given WebObject (WebPage, WebComponent, WebObject)
     * @param <T> generic object type
     * @return Initialized WebObject
     */
    public <T extends WebObject> T navigateTo(Class<T> webObjectClass) {
        return WebObjectFactory.initWebObject(webObjectClass, getProps().getAppInstance());
    }

    public WebDriver getDriver() {
        return getAppInstance().getWebDriverManager().getDriver();
    }

    public WebAppInstance<?> getAppInstance() {
        return props.getAppInstance();
    }

    public LanguageManager getLanguageManager() {
        return getAppInstance().getLanguageManager();
    }
}
