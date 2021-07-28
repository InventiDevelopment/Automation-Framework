package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.data.enums.ProxyScheme;
import cz.inventi.qa.framework.core.data.enums.web.Browser;
import cz.inventi.qa.framework.core.data.web.Timeouts;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.webdrivers.ChromeWebWebDriver;
import cz.inventi.qa.framework.core.webdrivers.WebDriverWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Paths;


public class WebDriverManager {
    private final AppInstance appInstance;
    private WebDriverWrapper webDriverWrapper;

    public WebDriverManager(AppInstance appInstance) {
        this.appInstance = appInstance;
    }

    public void init() {
        Browser currentBrowser = appInstance.getTestVariablesManager().getWebAppVariables().getBrowser();
        setWebDriverProxyVariables();
        switch (currentBrowser) {
            case CHROME -> webDriverWrapper = new ChromeWebWebDriver(appInstance);
            default -> throw new FrameworkException("Browser '" + currentBrowser + "' support currently not implemented.");
        }
        initializeWebDriver(appInstance.getConfigManager().getCurrentApplicationEnvironmentUrl());
    }

    /**
     * Sets global proxy variables, if they are received in
     * Maven command. WebDriverManager collects them automatically.
     * ProxyScheme variable sets HTTP protocol type and projects
     * into created variables' names.
     */
    private void setWebDriverProxyVariables() {
        if (FrameworkManager.getProxySettings() != null) {
            Log.info("Selenium WebDriver's proxy variables set");
            String proxyServer = FrameworkManager.getProxySettings().getProxyServer();
            String proxyUser = FrameworkManager.getProxySettings().getProxyUser();
            String proxyPass = FrameworkManager.getProxySettings().getProxyPass();
            ProxyScheme proxyScheme = FrameworkManager.getProxySettings().getProxyScheme();

            System.setProperty(proxyScheme + "_PROXY", proxyServer);
            if (proxyUser != null && proxyPass != null) {
                System.setProperty(proxyScheme + "_PROXY", proxyUser);
                System.setProperty(proxyScheme + "_PROXY", proxyPass);
            }
        }
    }

    /**
     * Initializes WebDriver redirected to given URL.
     * @param appUrl URL
     */
    private void initializeWebDriver(String appUrl) {
        try {
            getDriver().get(formatLocalResourcesURL(appUrl));
        } catch (Exception e) {
            throw new FrameworkException("Unable to get url: '" + appUrl + "'. Please check that url is valid.");
        }
    }

    /**
     * Mainly for testing purposes of the framework. Formats URL
     * to access local directory in the project.
     * @param appUrl URL with "test://" or "main://" keywords
     * @return Path to the local directory
     */
    private String formatLocalResourcesURL(String appUrl) {
        if (appUrl.contains("test://") || appUrl.contains("main://")) {
            String[] resourcePackage = appUrl.split("://");
            return new File(Paths.get("src",resourcePackage[0], "resources") + "/" + resourcePackage[1]).getAbsolutePath();
        }
        return appUrl;
    }

    /**
     * Retrieve current wrapper's WebDriver instance.
     * @return WebDriver
     */
    public WebDriver getDriver() {
        return webDriverWrapper.getDriver();
    }

    /**
     * Retrieve current wrapper's WebDriverWait instance.
     * @return WebDriverWait
     */
    public WebDriverWait getWait() {
        return webDriverWrapper.getWait();
    }

    /**
     * Quits web driver.
     */
    public void quitDriver() {
        webDriverWrapper.getDriver().quit();
    }

    /**
     * Returns WebDriver timeouts either according to the
     * values defined in the WebDriver config file, or,
     * if found none, default hardcoded values.
     * @return Timeouts object with defined values
     */
    public Timeouts getTimeouts() {
        Timeouts configTimeouts = appInstance.getConfigManager().getWebDriverConfigData().getGeneralSettings().getWait().getTimeouts();
        boolean waitAutomatically = appInstance.getConfigManager().getWebDriverConfigData().getGeneralSettings().getWait().waitsAutomatically();
        if (configTimeouts == null) {
            if (waitAutomatically) {
                return new Timeouts(5000,10000,20000);
            } else {
                return new Timeouts(0,0,0);
            }
        } else {
            return configTimeouts;
        }
    }
}
