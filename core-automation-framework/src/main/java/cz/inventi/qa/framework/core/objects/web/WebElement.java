package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.data.web.Timeouts;
import cz.inventi.qa.framework.core.factories.webelement.WebElementFactory;
import cz.inventi.qa.framework.core.factories.webelement.WebElementFieldDecorator;
import cz.inventi.qa.framework.core.factories.webelement.WebElementLocator;
import cz.inventi.qa.framework.core.factories.webelement.WebElementLocatorFactory;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.lang.reflect.Field;
import java.sql.Driver;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebElement implements org.openqa.selenium.WebElement {
    private WebDriver driver;
    private JavascriptExecutor jsExec;
    private org.openqa.selenium.WebElement selWebElement;
    private WebElementLocator webElementLocator;
    private Actions actions;
    private Timeouts timeouts;

    public WebElement(org.openqa.selenium.WebElement selWebElement, WebElementLocator webElementLocator) {
        this.selWebElement = selWebElement;
        this.webElementLocator = webElementLocator;
        driver = DriverManager.getDriver();
        jsExec = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        timeouts = ConfigManager.getDriverConfigData().getGeneralSettings().getWait().getTimeouts();
    }

    public String getXpath () {
        return webElementLocator.getXpath();
    }

    public void clickJS() {
        jsExec.executeScript("arguments[0].click();", webElementLocator.findElement());
    }

    public void hover() {
        waitUntilDisplayed();
        actions
            .moveToElement(selWebElement)
            .build()
            .perform();
    }

    public void scrollTo() {
        jsExec.executeScript("arguments[0].scrollIntoView(true);", webElementLocator.findElement());
    }

    @Override
    public void click() {
        waitUntilClickable();
        getFluentWait(timeouts.getMin()).<Boolean>until(webDriver -> {
            selWebElement.click();
            return true;
        });
    }

    @Override
    public void submit() {
        selWebElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        waitUntilClickable();
        click();
        selWebElement.sendKeys();
    }

    @Override
    public void clear() {
        waitUntilClickable();
        click();
        selWebElement.clear();
    }

    @Override
    public String getTagName() {
        return selWebElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        return selWebElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        return selWebElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return selWebElement.isEnabled();
    }

    @Override
    public String getText() {
        return selWebElement.getText();
    }

    @Override
    public List<org.openqa.selenium.WebElement> findElements(By by) {
        return selWebElement.findElements(By.xpath(by.toString()));
    }

    @Override
    public org.openqa.selenium.WebElement findElement(By by) {
        return selWebElement.findElement(By.xpath(by.toString()));
    }

    public List<WebElement> findElements(String xpath) {
        List<WebElement> webElements = new ArrayList<>();

        for (org.openqa.selenium.WebElement selElement : selWebElement.findElements(By.xpath(xpath))) {
            webElements.add((WebElement) selElement);
        }
        return webElements;
    }

    public WebElement findElement(String findElementXpath) {
        return new WebElement(selWebElement.findElement(By.xpath(findElementXpath)), new WebElementLocator(DriverManager.getDriver(), getXpath() + findElementXpath, 0));
    }

    @Override
    public boolean isDisplayed() {
        return selWebElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return selWebElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return selWebElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return selWebElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        return selWebElement.getCssValue(propertyName);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return selWebElement.getScreenshotAs(target);
    }

    public org.openqa.selenium.WebElement getSelWebElement() {
        return selWebElement;
    }

    private FluentWait<WebDriver> getFluentWait (int timeout) {
        if (!ConfigManager.getDriverConfigData().waitsAutomatically()) {
            timeout = 0;
        }

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(timeout))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    public boolean isClickable () {
        return ExpectedConditions.elementToBeClickable(this).apply(driver) != null;
    }

    public void waitUntilClickable () {
        waitUntilDisplayed();

        if (!isClickable()) {
            getFluentWait(timeouts.getMin())
                    .withMessage("WebElement " + getXpath() + " is not clickable.")
                    .until(webDriver -> isClickable());
        }
    }

    public void waitUntilIsEnabled () {
        if (!isEnabled()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is not enabled.")
                    .until(webDriver -> isEnabled());
        }
    }

    public void waitUntilIsDisabled () {
        if (isEnabled()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is still enabled.")
                    .until(webDriver -> !isEnabled());
        }
    }

    public void waitUntilNotClickable () {
        if (isClickable()) {
            getFluentWait(timeouts.getMin())
                    .withMessage("WebElement " + getXpath() + " is still clickable.")
                    .until(webDriver -> !isClickable());
        }
    }

    public void waitUntilDisplayed () {
        if (!isDisplayed()) {
            scrollTo();
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is not displayed.")
                    .until(webDriver -> isDisplayed());
        }
    }

    public void waitUntilNotDisplayed () {
        if (isDisplayed()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is still displayed.")
                    .until(webDriver -> !isDisplayed());
        }
    }
}
