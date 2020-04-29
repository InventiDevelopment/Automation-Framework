package cz.inventi.sample.framework.core.factories.webelement;

import cz.inventi.sample.framework.core.data.web.GeneralSettings;
import cz.inventi.sample.framework.core.factories.PageBuilder;
import cz.inventi.sample.framework.core.managers.ConfigManager;
import cz.inventi.sample.framework.core.annotations.FindElement;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.FluentWait;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.List;

public class WebElementLocator implements ElementLocator {
    private final WebDriver driver;
    private String xpath;
    private FindElement elementAnnotation;
    private GeneralSettings generalDriverSettings;

    public WebElementLocator(final WebDriver driver, final Field field) {
        this.driver = driver;
        generalDriverSettings = ConfigManager.getDriverConfigData().getGeneralSettings();
        elementAnnotation = field.getAnnotation(FindElement.class);
        xpath = PageBuilder.generateIndexedXpath(elementAnnotation.xpath(), elementAnnotation.index());
    }

    @Override
    public WebElement findElement() {
        try {
            if (generalDriverSettings.getWait().waitsAutomatically()) {
                return getFluentWait().until(webDriver -> driver.findElement(By.xpath(xpath)));
            }
            return driver.findElement(By.xpath(xpath));
        } catch (TimeoutException e) {
            throw new RuntimeException("Could not find WebElement with xpath: '" + xpath + "'");
        }
    }

    @Override
    public List<WebElement> findElements() {
        try {
            if (generalDriverSettings.getWait().waitsAutomatically()) {
                return getFluentWait().until(webDriver -> driver.findElements(By.xpath(xpath)));
            }
            return driver.findElements(By.xpath(xpath));
        } catch (TimeoutException e) {
            throw new RuntimeException("Could not find WebElements group with xpath: '" + xpath + "'");
        }
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public String getXpath() {
        return xpath;
    }

    public void setParentXpath(String parentXpath) {
        xpath = PageBuilder.generateIndexedXpath(parentXpath + elementAnnotation.xpath(), elementAnnotation.index());
    }

    private FluentWait<WebDriver> getFluentWait () {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(generalDriverSettings.getWait().getTimeouts().getMax()))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
    }
}
