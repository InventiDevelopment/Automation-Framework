package cz.inventi.qa.framework.core.annotations.web.handlers;

import cz.inventi.qa.framework.core.annotations.web.FindElement;

import java.lang.annotation.Annotation;

/**
 * Handler for FindElement annotation.
 */
public class FindElementHandler implements FindElement {
    private int index;
    private String xpath;

    public FindElementHandler(String xpath, int index) {
        this.index = index;
        this.xpath = xpath;
    }

    @Override
    public String xpath() {
        return xpath;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return this.getClass();
    }
}
