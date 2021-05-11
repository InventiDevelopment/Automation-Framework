package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.data.web.Timeouts;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.data.enums.web.Browser;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
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
        switch (currentBrowser) {
            case CHROME:
                webDriverWrapper = new ChromeWebWebDriver(appInstance);
                break;
            default:
                Log.fail("Browser '" + currentBrowser + "' support currently not implemented.");
        }
        initializeWebDriver(appInstance.getConfigManager().getCurrentApplicationEnvironmentUrl());
    }

    private void initializeWebDriver(String appUrl) {
        try {
            getDriver().get(formatLocalResourcesURL(appUrl));
        } catch (Exception e) {
            Log.fail("Unable to get url: '" + appUrl + "'. Please check that url is valid.");
        }
    }

    private String formatLocalResourcesURL(String appUrl) {
        if (appUrl.contains("test://") || appUrl.contains("main://")) {
            String[] resourcePackage = appUrl.split("://");
            return new File(Paths.get("src",resourcePackage[0], "resources") + "/" + resourcePackage[1]).getAbsolutePath();
        }
        return appUrl;
    }

    public WebDriver getDriver() {
        return webDriverWrapper.getDriver();
    }

    public WebDriverWait getWait() {
        return webDriverWrapper.getWait();
    }

    public void cleanDriver() {
        webDriverWrapper.getDriver().quit();
    }

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
