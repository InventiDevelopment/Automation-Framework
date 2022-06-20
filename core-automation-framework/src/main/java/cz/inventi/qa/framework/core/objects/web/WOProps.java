package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.factories.web.PageBuilder;

public class WOProps {
    private final WebAppInstance<?> appInstance;
    private String xpath = "";
    private Object returnKlass;
    private WOProps parentProps;

    public WOProps(String xpath, WebAppInstance<?> appInstance) {
        this.xpath = xpath;
        this.appInstance = appInstance;
    }

    public WOProps(String xpath, Object returnKlass, WOProps parentProps, WebAppInstance<?> appInstance) {
        this(xpath, appInstance);
        this.returnKlass = returnKlass;
        this.parentProps = parentProps;
    }

    public WOProps(FindElement findElement, Object returnKlass, WebAppInstance<?> appInstance) {
        this(findElement, appInstance);
        this.returnKlass = returnKlass;
    }

    public WOProps(FindElement findElement, WebAppInstance<?> appInstance) {
        this(PageBuilder.generateIndexedXpath(findElement), appInstance);
    }

    public WOProps(FindElement findElement, WOProps parentProps, WebAppInstance<?> appInstance) {
        this(findElement, appInstance);
        this.parentProps = parentProps;
    }

    public String getXpath() {
        return xpath;
    }

    public Object getReturnKlass() {
        return returnKlass;
    }

    public WebAppInstance<?> getAppInstance() {
        return appInstance;
    }

    public  <T extends WebPage> Class<T> getPage() {
        return parentProps.getPage();
    }
}
