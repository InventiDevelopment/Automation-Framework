package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.annotations.web.handlers.FindElementHandler;
import cz.inventi.qa.framework.core.factories.web.PageBuilder;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
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
    private final Class<R> componentClass;
    private final Object returnComponent;
    private final String componentXpath;
    private final WOProps props;
    private List<R> componentsList;

    public WebComponentList(Class<?> componentClass, WOProps props) {
        this.returnComponent = props.getReturnKlass();
        this.props = props;
        this.componentClass = (Class<R>) componentClass;
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
        if (getAppInstance().getConfigManager().getWebDriverConfigData().waitsAutomatically()) {
            int maxWait = getAppInstance().getConfigManager().getWebDriverConfigData().getTimeouts().getMax();
            FluentWait<WebDriver> fluentWait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofMillis(maxWait))
                    .pollingEvery(Duration.ofMillis(100))
                    .ignoring(NoSuchElementException.class)
                    .ignoring(StaleElementReferenceException.class)
                    .withMessage("WebComponentList of '" + componentClass + "' with xpath '" + componentXpath +
                            "' could not be found on page.");
            fluentWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(componentXpath), 0));
        }
        return getDriver().findElements(By.xpath(componentXpath)).size();
    }

    private WOProps getWOProps (int xpathIndex) {
        FindElementHandler componentXpathWithIndex = new FindElementHandler(componentXpath, xpathIndex);
        String parentXpath = props.getXpath();
        return new WOProps(
                PageBuilder.generateXpathWithParent(parentXpath, componentXpathWithIndex),
                returnComponent,
                props,
                getAppInstance()
        );
    }

    public WebDriver getDriver() {
        return props.getAppInstance().getWebDriverManager().getDriver();
    }

    public WebAppInstance<?> getAppInstance() {
        return props.getAppInstance();
    }
}