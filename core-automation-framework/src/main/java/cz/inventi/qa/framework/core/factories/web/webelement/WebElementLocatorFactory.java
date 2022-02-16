package cz.inventi.qa.framework.core.factories.web.webelement;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.web.WebAppInstance;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class WebElementLocatorFactory implements ElementLocatorFactory {
    private final WebAppInstance<?> appInstance;

    public WebElementLocatorFactory(WebAppInstance<?> appInstance) {
        this.appInstance = appInstance;
    }

    @Override
    public ElementLocator createLocator(final Field field) {
        if (field.isAnnotationPresent(FindElement.class)) {
            return new WebElementLocator(field, appInstance);
        } else {
            throw new FrameworkException("Cannot process WebElement object with no @FindElement annotation." +
                    " Please check that all the WebElement objects have correctly defined @FindElement annotation.");
        }
    }
}
