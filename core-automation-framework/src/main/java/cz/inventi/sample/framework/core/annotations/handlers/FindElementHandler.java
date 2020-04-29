package cz.inventi.sample.framework.core.annotations.handlers;

import cz.inventi.sample.framework.core.annotations.FindElement;

import java.lang.annotation.Annotation;

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
