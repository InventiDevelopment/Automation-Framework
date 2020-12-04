package cz.inventi.qa.framework.core.factories.webelement;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.managers.DriverManager;
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
