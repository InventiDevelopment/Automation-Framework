package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.factories.PageBuilder;

public class WOProps<T extends WebPage> {
    private String xpath = "";
    private Object returnKlass;
    private Class<T> page;

    public WOProps(String xpath, Object returnKlass, WOProps<T> parentProps) {
        this.xpath = xpath;
        this.returnKlass = returnKlass;
        this.page = parentProps.getPage();
    }

    public WOProps(String xpath) {
        this.xpath = xpath;
    }

    public WOProps(FindElement findElementAnnotation, Class<T> webPage) {
        if (findElementAnnotation != null) {
            this.xpath = PageBuilder.generateIndexedXpath(findElementAnnotation);
        }
        this.page = webPage;
    }

    public String getXpath() {
        return xpath;
    }

    public Object getReturnKlass() {
        return returnKlass;
    }

    public Class<T> getPage() {
        return page;
    }
}
