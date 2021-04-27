package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.Log;
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
        Browser currentBrowser = appInstance.getParametersManager().getWebAppParameters().getBrowser();
        switch (currentBrowser) {
            case CHROME:
                webDriverWrapper = new ChromeWebWebDriver(appInstance);
                break;
            default:
                Log.fail("Browser '" + currentBrowser + "' support currently not implemented.");
        }
        initializeWebDriver(appInstance.getAppManager().getAppUrl());
    }

    private void initializeWebDriver (String appUrl) {
        try {
            getDriver().get(formatResourcesURL(appUrl));
        } catch (Exception e) {
            Log.fail("Unable to get url: '" + appUrl + "'. Please check that url is valid.");
        }
    }

    private String formatResourcesURL (String appUrl) {
        if (appUrl.contains("test://") || appUrl.contains("main://")) {
            String[] resourcePackage = appUrl.split("://");
            return appUrl = new File(Paths.get("src",resourcePackage[0], "resources") + "/" + resourcePackage[1]).getAbsolutePath();
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
}
