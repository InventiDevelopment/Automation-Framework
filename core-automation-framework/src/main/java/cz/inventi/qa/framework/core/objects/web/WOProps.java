package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.factories.PageBuilder;

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

    public WOProps(FindElement findElementAnnotation, Object returnKlass) {
        this(findElementAnnotation);
        this.returnKlass = returnKlass;
    }

    public WOProps(FindElement findElementAnnotation) {
        this(PageBuilder.generateIndexedXpath(findElementAnnotation));
    }

    public WOProps(FindElement findElementAnnotation, WOProps parentProps) {
        this(findElementAnnotation);
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
