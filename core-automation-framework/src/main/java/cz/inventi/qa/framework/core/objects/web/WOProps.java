package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.factories.PageBuilder;

public class WOProps {
    private String xpath = "";
    private Object returnKlass;

    public WOProps(String xpath, Object returnKlass) {
        this.xpath = xpath;
        this.returnKlass = returnKlass;
    }

    public WOProps(String xpath) {
        this.xpath = xpath;
    }

    public WOProps(FindElement findElementAnnotation) {
        if (findElementAnnotation != null) {
            this.xpath = PageBuilder.generateIndexedXpath(findElementAnnotation);
        }
    }

    public String getXpath() {
        return xpath;
    }

    public Object getReturnKlass() {
        return returnKlass;
    }
}
