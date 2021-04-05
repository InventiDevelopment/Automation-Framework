package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.annotations.web.handlers.FindElementHandler;
import cz.inventi.qa.framework.core.factories.web.PageBuilder;

public abstract class WebObject {
    private WOProps props;

    public WebObject(WOProps props) {
        this.props = props;
        PageBuilder.initElements(this, props);
    }

    public String getXpath () {
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
}
