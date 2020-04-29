package cz.inventi.sample.framework.core.factories.webelement;

import cz.inventi.sample.framework.core.managers.DriverManager;
import cz.inventi.sample.framework.core.annotations.FindElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

public class WebElementLocatorFactory implements ElementLocatorFactory {

    @Override
    public ElementLocator createLocator(final Field field) {
        if (field.isAnnotationPresent(FindElement.class)) {
            return new WebElementLocator(DriverManager.getDriver(), field);
        } else {
            throw new RuntimeException("Cannot process WebElement object with no @FindElement annotation. Please check that all the WebElement objects have correctly defined @FindElement annotation.");
        }
    }
}
