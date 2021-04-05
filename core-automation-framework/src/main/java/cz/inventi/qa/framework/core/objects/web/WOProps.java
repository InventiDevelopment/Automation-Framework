package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.factories.web.PageBuilder;

public class WOProps {
    private String xpath = "";
    private Object returnKlass;
    private WOProps parentProps;

    public WOProps(String xpath) {
        this.xpath = xpath;
    }

    public WOProps(String xpath, Object returnKlass, WOProps parentProps) {
        this.xpath = xpath;
        this.returnKlass = returnKlass;
        this.parentProps = parentProps;
    }

    public WOProps(FindElement findElement, Object returnKlass) {
        this(findElement);
        this.returnKlass = returnKlass;
    }

    public WOProps(FindElement findElement) {
        this(PageBuilder.generateIndexedXpath(findElement));
    }

    public WOProps(FindElement findElement, WOProps parentProps) {
        this(findElement);
        this.parentProps = parentProps;
    }

    public String getXpath() {
        return xpath;
    }

    public Object getReturnKlass() {
        return returnKlass;
    }

    public  <T extends WebPage> Class<T> getPage() {
        return parentProps.getPage();
    }
}
