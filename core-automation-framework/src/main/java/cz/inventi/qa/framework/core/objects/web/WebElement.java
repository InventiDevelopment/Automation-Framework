package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.data.web.Timeouts;
import cz.inventi.qa.framework.core.factories.web.webelement.WebElementLocator;
import cz.inventi.qa.framework.core.managers.ReportManager;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
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
public class WebElement implements org.openqa.selenium.WebElement {
    private static final int MAX_RETRIES = 5;
    private final WebDriver driver;
    private final JavascriptExecutor jsExec;
    private final WebElementLocator webElementLocator;
    private final Actions actions;
    private final AppInstance appInstance;
    private final Timeouts timeouts;
    private org.openqa.selenium.WebElement selWebElement;

    public WebElement(org.openqa.selenium.WebElement selWebElement, WebElementLocator webElementLocator, AppInstance appInstance) {
        this.selWebElement = selWebElement;
        this.webElementLocator = webElementLocator;
        this.appInstance = appInstance;
        driver = appInstance.getWebDriverManager().getDriver();
        jsExec = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        timeouts = appInstance.getWebDriverManager().getTimeouts();
    }

    public String getXpath () {
        return webElementLocator.getXpath();
    }

    public void clickJS() {
        printAction();
        checkElementState();
        jsExec.executeScript("arguments[0].click();", webElementLocator.findElement());
        afterAction(true);
    }

    public void hover() {
        printAction();
        checkElementState();
        actions
            .moveToElement(selWebElement)
            .build()
            .perform();
        afterAction(true);
    }

    public void scrollTo() {
        printAction();
        checkElementState();
        jsExec.executeScript("arguments[0].scrollIntoView(true);", webElementLocator.findElement());
        afterAction(true);
    }

    @Override
    public void click() {
        printAction();
        checkElementState();
        waitUntilClickable();
        getFluentWait(timeouts.getMin()).<Boolean>until(webDriver -> {
            selWebElement.click();
            return true;
        });
        afterAction(true);
    }

    @Override
    public void submit() {
        printAction();
        checkElementState();
        selWebElement.submit();
        afterAction(true);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        printAction();
        checkElementState();
        waitUntilClickable();
        click();
        selWebElement.sendKeys();
        afterAction(false);
    }

    @Override
    public void clear() {
        printAction();
        checkElementState();
        waitUntilClickable();
        selWebElement.clear();
        afterAction(false);
    }

    @Override
    public String getTagName() {
        checkElementState();
        return selWebElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        checkElementState();
        return selWebElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        checkElementState();
        return selWebElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return selWebElement.isEnabled();
    }

    @Override
    public String getText() {
        printAction();
        checkElementState();
        String elementText = selWebElement.getText();
        afterAction(true);
        return elementText;
    }

    @Override
    public List<org.openqa.selenium.WebElement> findElements(By by) {
        printAction(by.toString());
        List<org.openqa.selenium.WebElement> foundElements = selWebElement.findElements(By.xpath(by.toString()));
        afterAction(true);
        return foundElements;
    }

    @Override
    public org.openqa.selenium.WebElement findElement(By by) {
        printAction(by.toString());
        org.openqa.selenium.WebElement foundElement = selWebElement.findElement(By.xpath(by.toString()));
        afterAction(true);
        return foundElement;
    }

    public List<WebElement> findElements(String findElementsXpath) {
        List<WebElement> webElements = new ArrayList<>();
        printAction(findElementsXpath);
        for (org.openqa.selenium.WebElement selElement : selWebElement.findElements(By.xpath(findElementsXpath))) {
            webElements.add(new WebElement(selWebElement,
                    new WebElementLocator(getXpath() + findElementsXpath, 0, appInstance), appInstance));
        }
        afterAction(true);
        return webElements;
    }

    public WebElement findElement(String findElementXpath) {
        printAction(findElementXpath);
        return new WebElement(selWebElement.findElement(By.xpath(findElementXpath)),
                new WebElementLocator(getXpath() + findElementXpath, 0, appInstance), appInstance);
    }

    @Override
    public boolean isDisplayed() {
        printAction();
        checkElementState();
        boolean isDisplayed = selWebElement.isDisplayed();
        afterAction(true);
        return isDisplayed;
    }

    @Override
    public Point getLocation() {
        printAction();
        checkElementState();
        Point point = selWebElement.getLocation();
        afterAction(true);
        return point;
    }

    @Override
    public Dimension getSize() {
        printAction();
        checkElementState();
        Dimension dimension = selWebElement.getSize();
        afterAction(false);
        return dimension;
    }

    @Override
    public Rectangle getRect() {
        printAction();
        checkElementState();
        Rectangle rect = selWebElement.getRect();
        afterAction(false);
        return rect;
    }

    @Override
    public String getCssValue(String propertyName) {
        printAction();
        checkElementState();
        String cssValue = selWebElement.getCssValue(propertyName);
        afterAction(false);
        return cssValue;
    }

    @Override
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
        if (!appInstance.getConfigManager().getWebDriverConfigData().waitsAutomatically()) {
            timeout = 0;
        }

        return new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(timeout))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementNotInteractableException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    public boolean isClickable () {
        checkElementState();
        return ExpectedConditions.elementToBeClickable(this).apply(driver) != null;
    }

    public void waitUntilClickable () {
        printAction();
        waitUntilIsDisplayed();
        if (!isClickable()) {
            getFluentWait(timeouts.getMin())
                    .withMessage("WebElement " + getXpath() + " is not clickable.")
                    .until(webDriver -> isClickable());
        }
        afterAction(true);
    }

    public void waitUntilIsEnabled () {
        printAction();
        if (!isEnabled()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is not enabled.")
                    .until(webDriver -> isEnabled());
        }
        afterAction(true);
    }

    public void waitUntilIsDisabled () {
        printAction();
        if (isEnabled()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is still enabled.")
                    .until(webDriver -> !isEnabled());
        }
        afterAction(true);
    }

    public void waitUntilNotClickable () {
        printAction();
        if (isClickable()) {
            getFluentWait(timeouts.getMin())
                    .withMessage("WebElement " + getXpath() + " is still clickable.")
                    .until(webDriver -> !isClickable());
        }
        afterAction(true);
    }

    public void waitUntilIsDisplayed() {
        printAction();
        if (!isDisplayed()) {
            scrollTo();
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is not displayed.")
                    .until(webDriver -> isDisplayed());
        }
        afterAction(true);
    }

    public void waitUntilIsNotDisplayed() {
        printAction();
        if (isDisplayed()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is still displayed.")
                    .until(webDriver -> !isDisplayed());
        }
        afterAction(true);
    }

    private void printAction() {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.debug("Trying to do '" + actionName + "' action on element with XPATH: '" + getXpath() + "'");
    }

    private void printAction(String inputValue) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.debug("Trying to do '" + actionName + "' action with input value '" + inputValue + "' on element with XPATH: '" + getXpath() + "'");
    }

    private void afterAction(boolean takeScreenshot) {
        String actionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        Log.debug("Performed '" + actionName + "' action on element with XPATH: '" + getXpath() + "'");
        if (takeScreenshot) {
            ReportManager.addWebAppScreenshot("Action " + actionName, webElementLocator, appInstance);
        }
    }

    private void refreshElement() {
        this.selWebElement = driver.findElement(By.xpath(getXpath()));
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