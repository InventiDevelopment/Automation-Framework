package cz.inventi.qa.framework.core.objects.web;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.data.web.Timeouts;
import cz.inventi.qa.framework.core.factories.webelement.WebElementLocator;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebElement implements org.openqa.selenium.WebElement {
    private static final int MAX_RETRIES = 5;
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
        printAction();
        checkElementState();
        jsExec.executeScript("arguments[0].click();", webElementLocator.findElement());
    }

    public void hover() {
        printAction();
        checkElementState();
        actions
            .moveToElement(selWebElement)
            .build()
            .perform();
    }

    public void scrollTo() {
        printAction();
        checkElementState();
        jsExec.executeScript("arguments[0].scrollIntoView(true);", webElementLocator.findElement());
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
    }

    @Override
    public void submit() {
        printAction();
        checkElementState();
        selWebElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        printAction();
        checkElementState();
        waitUntilClickable();
        click();
        selWebElement.sendKeys();
    }

    @Override
    public void clear() {
        printAction();
        checkElementState();
        waitUntilClickable();
        selWebElement.clear();
    }

    @Override
    public String getTagName() {
        checkElementState();
        return selWebElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        printAction();
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
        return selWebElement.getText();
    }

    @Override
    public List<org.openqa.selenium.WebElement> findElements(By by) {
        printAction(by.toString());
        return selWebElement.findElements(By.xpath(by.toString()));
    }

    @Override
    public org.openqa.selenium.WebElement findElement(By by) {
        printAction(by.toString());
        return selWebElement.findElement(By.xpath(by.toString()));
    }

    public List<WebElement> findElements(String findElementsXpath) {
        List<WebElement> webElements = new ArrayList<>();
        printAction(findElementsXpath);

        for (org.openqa.selenium.WebElement selElement : selWebElement.findElements(By.xpath(findElementsXpath))) {
            webElements.add(new WebElement(selWebElement, new WebElementLocator(DriverManager.getDriver(), getXpath() + findElementsXpath, 0)));
        }
        return webElements;
    }

    public WebElement findElement(String findElementXpath) {
        printAction(findElementXpath);
        return new WebElement(selWebElement.findElement(By.xpath(findElementXpath)), new WebElementLocator(DriverManager.getDriver(), getXpath() + findElementXpath, 0));
    }

    @Override
    public boolean isDisplayed() {
        checkElementState();
        return selWebElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        checkElementState();
        return selWebElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        checkElementState();
        return selWebElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        checkElementState();
        return selWebElement.getRect();
    }

    @Override
    public String getCssValue(String propertyName) {
        checkElementState();
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
                .ignoring(ElementNotInteractableException.class)
                .ignoring(ElementClickInterceptedException.class);
    }

    public boolean isClickable () {
        checkElementState();
        return ExpectedConditions.elementToBeClickable(this).apply(driver) != null;
    }

    public void waitUntilClickable () {
        printAction();
        waitUntilDisplayed();
        if (!isClickable()) {
            getFluentWait(timeouts.getMin())
                    .withMessage("WebElement " + getXpath() + " is not clickable.")
                    .until(webDriver -> isClickable());
        }
    }

    public void waitUntilIsEnabled () {
        printAction();
        if (!isEnabled()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is not enabled.")
                    .until(webDriver -> isEnabled());
        }
    }

    public void waitUntilIsDisabled () {
        printAction();
        if (isEnabled()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is still enabled.")
                    .until(webDriver -> !isEnabled());
        }
    }

    public void waitUntilNotClickable () {
        printAction();
        if (isClickable()) {
            getFluentWait(timeouts.getMin())
                    .withMessage("WebElement " + getXpath() + " is still clickable.")
                    .until(webDriver -> !isClickable());
        }
    }

    public void waitUntilDisplayed () {
        printAction();
        if (!isDisplayed()) {
            scrollTo();
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is not displayed.")
                    .until(webDriver -> isDisplayed());
        }
    }

    public void waitUntilNotDisplayed () {
        printAction();
        if (isDisplayed()) {
            getFluentWait(timeouts.getMid())
                    .withMessage("WebElement " + getXpath() + " is still displayed.")
                    .until(webDriver -> !isDisplayed());
        }
    }

    private void printAction() {
        Log.debug("Performing '" + Thread.currentThread().getStackTrace()[2].getMethodName() + "' action on element with XPATH: '" + getXpath() + "'");
    }

    private void printAction(String inputValue) {
        Log.debug("Performing '" + Thread.currentThread().getStackTrace()[2].getMethodName() + "' action with input value '" + inputValue + "' on element with XPATH: '" + getXpath() + "'");
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
