package cz.inventi.qa.framework.core.annotations.handlers;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.lang.annotation.Annotation;

public class FindByHandler implements FindBy {

    private String xpath;

    public FindByHandler(String xpath) {
        this.xpath = xpath;
    }

    @Override
    public How how() {
        return How.XPATH;
    }

    @Override
    public String using() {
        return "";
    }

    @Override
    public String id() {
        return "";
    }

    @Override
    public String name() {
        return "";
    }

    @Override
    public String className() {
        return "";
    }

    @Override
    public String css() {
        return "";
    }

    @Override
    public String tagName() {
        return "";
    }

    @Override
    public String linkText() {
        return "";
    }

    @Override
    public String partialLinkText() {
        return "";
    }

    @Override
    public String xpath() {
        return xpath;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return FindBy.class;
    }
}
