package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.data.enums.Browser;
import cz.inventi.qa.framework.core.webdrivers.ChromeWebWebDriver;
import cz.inventi.qa.framework.core.webdrivers.WebDriverWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Paths;


public class WebDriverManager {
    private static WebDriverWrapper webDriverWrapper;

    public static void init() {
        Browser currentBrowser = ParametersManager.getWebAppParameters().getBrowser();

        switch (currentBrowser) {
            case CHROME:
                webDriverWrapper = new ChromeWebWebDriver();
                break;
            default:
                Log.fail("Browser '" + currentBrowser + "' support currently not implemented.");
        }
        initializeWebDriver(AppManager.getAppUrl());
    }

    private static void initializeWebDriver (String appUrl) {
        try {
            formatResourcesURL(appUrl);
            getDriver().get(appUrl);
        } catch (Exception e) {
            Log.fail("Unable to get url: '" + appUrl + "'. Please check that url is valid.");
        }
    }

    private static void formatResourcesURL (String appUrl) {
        if (appUrl.contains("test://") || appUrl.contains("main://")) {
            String[] resourcePackage = appUrl.split("://");
            appUrl = new File(Paths.get("src",resourcePackage[0], "resources") + "/" + resourcePackage[1]).getAbsolutePath();
        }
    }

    public static WebDriver getDriver() {
        return webDriverWrapper.getDriver();
    }

    public static WebDriverWait getWait() {
        return webDriverWrapper.getWait();
    }

    public static void cleanDriver() {
        webDriverWrapper.getDriver().quit();
    }
}
