package cz.inventi.sample.framework.core.factories.webelement;

import cz.inventi.sample.framework.core.objects.web.WebElement;
import cz.inventi.sample.framework.core.objects.web.WebObject;
import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.annotations.NoParent;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WebElementFieldDecorator extends DefaultFieldDecorator implements FieldDecorator {
    private WebObject page;

    public WebElementFieldDecorator(final WebElementLocatorFactory factory, final Object page) {
        super(factory);
        this.page = (WebObject) page;
    }

    @Override
    public Object decorate(final ClassLoader loader, final Field field) {
        if (!WebElement.class.isAssignableFrom(field.getType()) && !isDecoratableList(field)) {
            return null;
        }

        if (field.getAnnotation(FindElement.class) == null) {
            return null;
        }

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

    protected boolean isDecoratableList(final Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        final Type genericType = field.getGenericType();

        if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        final Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        if (!WebElement.class.equals(listType)) {
            return false;
        }
        return true;
    }

    private void fetchParentXpath (WebElementLocator locator, Field field) {
        NoParent noParentAnnotation = field.getAnnotation(NoParent.class);
        if (noParentAnnotation == null || !noParentAnnotation.value()) locator.setParentXpath(page.getXpath());
    }

    private ArrayList<WebElement> extendWebElements (List<org.openqa.selenium.WebElement> listOfSelWebElements, WebElementLocator webElementLocator) {
        ArrayList<WebElement> webElements = new ArrayList<>();

        for (org.openqa.selenium.WebElement selWebElement : listOfSelWebElements) {
            webElements.add(extendWebElement(selWebElement, webElementLocator));
        }

        return webElements;
    }

    private WebElement extendWebElement (org.openqa.selenium.WebElement selWebElement, WebElementLocator webElementLocator) {
        return new WebElement(selWebElement, webElementLocator);
    }
}
