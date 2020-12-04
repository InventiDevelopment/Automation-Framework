package cz.inventi.qa.framework.core.factories.webelement;

import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WebElementLocatorHandler implements InvocationHandler {
    private final WebElementLocator locator;

    public WebElementLocatorHandler (WebElementLocator locator) {
        this.locator = locator;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        WebElement element;

        try {
            element = locator.findElement();
        } catch (Exception e) {
            if ("toString".equals(method.getName())) {
                return "Proxy select(element) for: " + locator.toString();
            }
            else throw e;
        }
        try {
            return method.invoke(element, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }
}
