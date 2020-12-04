package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.annotations.handlers.FindElementHandler;
import cz.inventi.qa.framework.core.factories.PageBuilder;
import cz.inventi.qa.framework.core.factories.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class WebComponentList<R extends WebComponent<?>> {
    private Class<R> componentClass;
    private Object returnComponent;
    private List<R> componentsList;
    private String componentXpath;

    public WebComponentList(Class<R> componentClass, Object returnComponent) {
        this.returnComponent = returnComponent;
        this.componentClass = componentClass;
        componentXpath = componentClass.getDeclaredAnnotation(FindElement.class).xpath();
    }

    public R get (int index) {
        return getComponents().get(index);
    }

    public List<R> getComponents () {
        if (componentsList == null) {
            initComponentList();
        }
        return componentsList;
    }

    private void initComponentList() {
        int numberOfComponents = getNumberOfComponents();
        componentsList = new ArrayList<>();

        for (int i = 1; i <= numberOfComponents; i++) {
            componentsList.add(WebObjectFactory.reflectionInitClassWithProps(componentClass, getWOProps(i)));
        }
    }

    private int getNumberOfComponents() {
        if (ConfigManager.getDriverConfigData().getGeneralSettings().getWait().waitsAutomatically()) {
            FluentWait<WebDriver> fluentWait = new FluentWait<>(DriverManager.getDriver())
                    .withTimeout(Duration.ofMillis(ConfigManager.getDriverConfigData().getGeneralSettings().getWait().getTimeouts().getMax()))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .withMessage("WebComponentList of '" + componentClass + "' with xpath '" + componentXpath + "' could not be found on page.");

            fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(componentXpath), 0));
        }
        return DriverManager.getDriver().findElements(By.xpath(componentXpath)).size();
    }

    private WOProps getWOProps (int xpathIndex) {
        FindElementHandler componentXpathWithIndex = new FindElementHandler(componentXpath, xpathIndex);
        String parentXpath = ((R) returnComponent).getXpath();
        return new WOProps(PageBuilder.generateXpathWithParent(parentXpath, componentXpathWithIndex), returnComponent);
    }
}