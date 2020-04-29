package cz.inventi.sample.framework.core.factories.webelement;

import cz.inventi.sample.framework.core.objects.web.WebObject;
import org.openqa.selenium.support.PageFactory;

public class WebElementFactory {

    public static <T extends WebObject> void initElements(final T page) {
        initElements(new WebElementLocatorFactory(), page);
    }

    public static <T extends WebObject> void initElements(final WebElementLocatorFactory factory, final T page) {
        PageFactory.initElements(new WebElementFieldDecorator(factory, page), page);
    }
}

