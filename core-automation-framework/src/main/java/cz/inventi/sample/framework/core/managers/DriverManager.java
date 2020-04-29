package cz.inventi.sample.framework.core.managers;

import cz.inventi.sample.framework.core.Log;
import cz.inventi.sample.framework.core.webdrivers.ChromeWebDriver;
import cz.inventi.sample.framework.core.webdrivers.DriverWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverManager {
    private static DriverWrapper driverWrapper;

    public static void init() {
        switch (ParametersManager.getBrowser()) {
            case CHROME:
                driverWrapper = new ChromeWebDriver();
                break;
            default:
                Log.fail("Browser '" + ParametersManager.getBrowser() + "' support currently not implemented.");
        }
    }

    public static WebDriver getDriver() {
        return driverWrapper.getDriver();
    }

    public static WebDriverWait getWait() {
        return driverWrapper.getWait();
    }

    public static void cleanDriver() {
        driverWrapper.getDriver().quit();
    }
}
