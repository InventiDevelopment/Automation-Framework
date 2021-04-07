package cz.inventi.qa.framework.core.factories.web.webelement;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.managers.WebDriverManager;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class WebElementLocatorFactory implements ElementLocatorFactory {
    private final AppInstance appInstance;

    public WebElementLocatorFactory(AppInstance appInstance) {
        this.appInstance = appInstance;
    }

    @Override
    public ElementLocator createLocator(final Field field) {
        if (field.isAnnotationPresent(FindElement.class)) {
            return new WebElementLocator(field, appInstance);
        } else {
            throw new RuntimeException("Cannot process WebElement object with no @FindElement annotation. Please check that all the WebElement objects have correctly defined @FindElement annotation.");
        }
    }
}
