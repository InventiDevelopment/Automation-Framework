package cz.inventi.sample.framework.core.objects.web;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.annotations.handlers.FindElementHandler;
import cz.inventi.sample.framework.core.factories.PageBuilder;
import cz.inventi.sample.framework.core.managers.DriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebObject {
    private WOProps props;

    public WebObject(WOProps props) {
        this.props = props;
        PageBuilder.initElements(this);
    }

    public String getXpath () {
        return props.getXpath();
    }

    public WOProps getProps() {
        return props;
    }

    public FindElement getFindElementAnnotation () {
        FindElement findElementAnnotation = getClass().getAnnotation(FindElement.class);

        if (findElementAnnotation == null) {
            return new FindElementHandler("", 0);
        }
        return getClass().getAnnotation(FindElement.class);
    }
}
