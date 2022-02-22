package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.data.web.Timeouts;
import cz.inventi.qa.framework.core.factories.web.webelement.WebElementLocator;
import cz.inventi.qa.framework.core.managers.ReportManager;
import cz.inventi.qa.framework.core.objects.framework.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom decorator for Selenium WebElement to provide
 * custom actions.
 */
public class WebElement<T extends WebObject> {
    private static final int MAX_RETRIES = 5;
    private final WebElementLocator webElementLocator;
    private final Actions actions;
    private final T parentWebObject;
    private org.openqa.selenium.WebElement selWebElement;

    public WebElement(
            org.openqa.selenium.WebElement selWebElement,
            WebElementLocator webElementLocator,
            T parentWebObject
    ) {
        this.selWebElement = selWebElement;
        this.webElementLocator = webElementLocator;
        this.parentWebObject = parentWebObject;
        actions = new Actions(getDriver());
    }

    public Timeouts getTimeouts() {
        return parentWebObject.getAppInstance().getWebDriverManager().getTimeouts();
    }

    public WebDriver getDriver() {
        return parentWebObject.getAppInstance().getWebDriverManager().getDriver();
    }

    public WebAppInstance<?> getAppInstance() {
        return parentWebObject.getAppInstance();
    }

    public JavascriptExecutor getJsExec() {
        return (JavascriptExecutor) getDriver();
    }

    public String getXpath () {
        return webElementLocator.getXpath();
    }

    public T clickJS() {
        printAction();
        checkElementState();
        getJsExec().executeScript("arguments[0].click();", webElementLocator.findElement());
        return afterAction(true);
    }

    public T hover() {
        printAction();
        checkElementState();
        actions
            .moveToElement(selWebElement)
            .build()
            .perform();
        return afterAction(true);
    }

    private T scrollTo(boolean takeScreenshot) {
        printAction();
        checkElementState();
        getJsExec().executeScript("arguments[0].scrollIntoView(true);", webElementLocator.findElement());
        return afterAction(takeScreenshot);
    }

    public T scrollTo() {
        return scrollTo(true);
    }

    private T click(boolean takeScreenshot) {
        printAction();
        checkElementState();
        waitUntilClickable(false);
        getFluentWait(getTimeouts().getMin()).<Boolean>until(webDriver -> {
            selWebElement.click();
            return true;
        });
        return afterAction(takeScreenshot);
    }

    public T click() {
        return click(true);
    }

    public T submit() {
        printAction();
        checkElementState();
        selWebElement.submit();
        return afterAction(true);
    }

    public T sendKeys(CharSequence... keysToSend) {
        printAction();
        checkElementState();
        waitUntilClickable(false);
        click(false);
        selWebElement.sendKeys(keysToSend);
        return afterAction(false);
    }

    public T clear() {
        return clear(true);
    }

    private T clear(boolean takeScreenshot) {
        printAction();
        checkElementState();
        waitUntilClickable(false);
        selWebElement.clear();
        return afterAction(takeScreenshot);
    }

    public String getTagName() {
        checkElementState();
        return selWebElement.getTagName();
    }

    public String getAttribute(String name) {
        checkElementState();
        return selWebElement.getAttribute(name);
    }

    public boolean isSelected() {
        checkElementState();
        return selWebElement.isSelected();
    }

    public boolean isEnabled() {
        return selWebElement.isEnabled();
    }

    public String getText() {
        printAction();
        checkElementState();
        String elementText = selWebElement.getText();
        afterAction(true);
        return elementText;
    }

    public List<org.openqa.selenium.WebElement> findElements(By by) {
        printAction(by.toString());
        List<org.openqa.selenium.WebElement> foundElements = selWebElement.findElements(By.xpath(by.toString()));
        afterAction(true);
        return foundElements;
    }

    public org.openqa.selenium.WebElement findElement(By by) {
        printAction(by.toString());
        org.openqa.selenium.WebElement foundElement = selWebElement.findElement(By.xpath(by.toString()));
        afterAction(true);
        return foundElement;
    }

    public List<WebElement<T>> findElements(String findElementsXpath) {
        List<WebElement<T>> webElements = new ArrayList<>();
        printAction(findElementsXpath);
        selWebElement.findElements(By.xpath(findElementsXpath)).forEach(selWebElement -> webElements.add(
                new WebElement<>(
                        selWebElement,
                        new WebElementLocator(
                                getXpath() + findElementsXpath,
                                0,
                                getAppInstance()
                        ),
                        parentWebObject
                )
        ));
        afterAction(true);
        return webElements;
    }

    public WebElement<T> findElement(String findElementXpath) {
        printAction(findElementXpath);
        return new WebElement<>(
                selWebElement.findElement(By.xpath(findElementXpath)),
                new WebElementLocator(
                        getXpath() + findElementXpath,
                        0,
                        getAppInstance()
                ),
                parentWebObject
        );
    }

    public boolean isDisplayed() {
        return isDisplayed(true);
    }

    private boolean isDisplayed(boolean takeScreenshot) {
        printAction();
        checkElementState();
        boolean isDisplayed = selWebElement.isDisplayed();
        afterAction(takeScreenshot);
        return isDisplayed;
    }

    public Point getLocation() {
        printAction();
        checkElementState();
        Point point = selWebElement.getLocation();
        afterAction(false);
        return point;
    }

    public Dimension getSize() {
        printAction();
        checkElementState();
        Dimension dimension = selWebElement.getSize();
        afterAction(false);
        return dimension;
    }

    public Rectangle getRect() {
        printAction();
        checkElementState();
        Rectangle rect = selWebElement.getRect();
        afterAction(false);
        return rect;
    }

    public String getCssValue(String propertyName) {
        printAction();
        checkElementState();
        String cssValue = selWebElement.getCssValue(propertyName);
        afterAction(false);
        return cssValue;
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        printAction();
        X screenShot = selWebElement.getScreenshotAs(target);
        afterAction(false);
        return screenShot;
    }

    public org.openqa.selenium.WebElement getSelWebElement() {
        return selWebElement;
    }

    private FluentWait<WebDriver> getFluentWait (int timeout) {
        if (!getAppInstance().getConfigManager().getWebDriverConfigData().waitsAutomatically()) {
            timeout = 0;
        }
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofMillis(timeout))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    public boolean isClickable () {
        checkElementState();
        return ExpectedConditions.elementToBeClickable(selWebElement).apply(getDriver()) != null;
    }

    public T waitUntilClickable () {
        return waitUntilClickable(true);
    }

    private T waitUntilClickable (boolean takeScreenshot) {
        printAction();
        waitUntilIsDisplayed(false);
        if (!isClickable()) {
            getFluentWait(getTimeouts().getMin())
                    .withMessage("WebElement " + getXpath() + " is not clickable.")
                    .until(webDriver -> isClickable());
        }
        return afterAction(takeScreenshot);
    }

    public T waitUntilIsEnabled () {
        return waitUntilIsEnabled(true);
    }

    private T waitUntilIsEnabled (boolean takeScreenshot) {
        printAction();
        if (!isEnabled()) {
            getFluentWait(getTimeouts().getMid())
                    .withMessage("WebElement " + getXpath() + " is not enabled.")
                    .until(webDriver -> isEnabled());
        }
        return afterAction(takeScreenshot);
    }

    public T waitUntilIsDisabled () {
        return waitUntilIsDisabled(true);
    }

    private T waitUntilIsDisabled (boolean takeScreenshot) {
        printAction();
        if (isEnabled()) {
            getFluentWait(getTimeouts().getMid())
                    .withMessage("WebElement " + getXpath() + " is still enabled.")
                    .until(webDriver -> !isEnabled());
        }
        return afterAction(takeScreenshot);
    }

    public T waitUntilNotClickable () {
        return waitUntilNotClickable(true);
    }

    private T waitUntilNotClickable (boolean takeScreenshot) {
        printAction();
        if (isClickable()) {
            getFluentWait(getTimeouts().getMin())
                    .withMessage("WebElement " + getXpath() + " is still clickable.")
                    .until(webDriver -> !isClickable());
        }
        return afterAction(takeScreenshot);
    }

    public T waitUntilIsDisplayed() {
        return waitUntilIsDisplayed(true);
    }

    private T waitUntilIsDisplayed(boolean takeScreenshot) {
        printAction();
        if (!isDisplayed(false)) {
            scrollTo(false);
            getFluentWait(getTimeouts().getMid())
                    .withMessage("WebElement " + getXpath() + " is not displayed.")
                    .until(webDriver -> isDisplayed(false));
        }
        return afterAction(takeScreenshot);
    }

    public T waitUntilIsNotDisplayed() {
        return waitUntilIsNotDisplayed(true);
    }

    private T waitUntilIsNotDisplayed(boolean takeScreenshot) {
        printAction();
        if (isDisplayed()) {
            getFluentWait(getTimeouts().getMid())
                    .withMessage("WebElement " + getXpath() + " is still displayed.")
                    .until(webDriver -> !isDisplayed(false));
        }
        return afterAction(takeScreenshot);
    }

    private void printAction() {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.debug("Trying to do '" + actionName + "' action on element with XPATH: '" + getXpath() + "'");
    }

    private void printAction(String inputValue) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.debug("Trying to do '" + actionName + "' action with input value '" + inputValue + "'" +
                " on element with XPATH: '" + getXpath() + "'");
    }

    private T afterAction(boolean takeScreenshot) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.debug("Performed '" + actionName + "' action on element with XPATH: '" + getXpath() + "'");
        if (takeScreenshot) {
            ReportManager.addWebAppScreenshot(
                    "Action " + actionName,
                    webElementLocator,
                    getAppInstance()
            );
        }
        return parentWebObject;
    }

    private void refreshElement() {
        this.selWebElement = getDriver().findElement(By.xpath(getXpath()));
    }

    private void checkElementState() {
        checkElementState(MAX_RETRIES);
    }

    private void checkElementState(int retries) {
        if (retries > 0) {
            try {
                isEnabled();
            } catch (StaleElementReferenceException e) {
                refreshElement();
                checkElementState(retries - 1);
            }
        } else {
            throw new RuntimeException("Cannot refresh stale element - '" + getXpath() + "'");
        }
    }
}