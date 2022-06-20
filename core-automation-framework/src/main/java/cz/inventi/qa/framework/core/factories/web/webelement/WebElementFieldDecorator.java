package cz.inventi.qa.framework.core.factories.web.webelement;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.annotations.web.NoParent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WebElementFieldDecorator<T extends WebObject> extends DefaultFieldDecorator implements FieldDecorator {
    private final T parentWebObject;

    public WebElementFieldDecorator(WebElementLocatorFactory factory, T parentWebObject) {
        super(factory);
        this.parentWebObject = parentWebObject;
    }

    @Override
    public Object decorate(ClassLoader loader, Field field) {
        if (!WebElement.class.isAssignableFrom(field.getType()) && !isDecoratableList(field)) return null;
        if (field.getAnnotation(FindElement.class) == null) return null;
        final WebElementLocator locator = (WebElementLocator) factory.createLocator(field);
        fetchParentXpath(locator, field);
        if (locator == null) {
            return null;
        } else if (WebElement.class.isAssignableFrom(field.getType())) {
            return extendWebElement(proxyForLocator(loader, locator), locator);
        } else if (List.class.isAssignableFrom(field.getType())) {
            return extendWebElements(proxyForListLocator(loader, locator), locator);
        } else {
            return null;
        }
    }

    protected boolean isDecoratableList(Field field) {
        if (!List.class.isAssignableFrom(field.getType())) return false;
        final Type genericType = field.getGenericType();
        return genericType instanceof ParameterizedType;
    }

    private void fetchParentXpath (WebElementLocator locator, Field field) {
        NoParent noParent = field.getAnnotation(NoParent.class);
        if (noParent == null || !noParent.value()) locator.setParentXpath(parentWebObject.getXpath());
    }

    private ArrayList<WebElement<T>> extendWebElements (
            List<org.openqa.selenium.WebElement> listOfSelWebElements,
            WebElementLocator webElementLocator
    ) {
        ArrayList<WebElement<T>> webElements = new ArrayList<>();
        listOfSelWebElements.forEach(selWebElement ->
                webElements.add(extendWebElement(selWebElement, webElementLocator))
        );
        return webElements;
    }

    private WebElement<T> extendWebElement (
            org.openqa.selenium.WebElement selWebElement,
            WebElementLocator webElementLocator
    ) {
        return new WebElement<>(selWebElement, webElementLocator, parentWebObject);
    }
}
