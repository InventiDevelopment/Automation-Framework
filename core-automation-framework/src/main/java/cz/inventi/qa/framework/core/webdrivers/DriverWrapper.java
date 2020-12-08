package cz.inventi.qa.framework.core.webdrivers;

import cz.inventi.qa.framework.core.data.enums.WindowSize;
import cz.inventi.qa.framework.core.data.web.GeneralSettings;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;

public abstract class DriverWrapper {
    private WebDriver driver;
    private WebDriverWait wait;
    private GeneralSettings generalSettings;
    private LoggingPreferences loggingPreferences;

    public DriverWrapper() {
        generalSettings = ConfigManager.getDriverConfigData().getGeneralSettings();
        checkCustomTargetPath();
        setLoggingPreferences();
    }

    private void setLoggingPreferences() {
        loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.BROWSER, Level.ALL);
        loggingPreferences.enable(LogType.CLIENT, Level.ALL);
        loggingPreferences.enable(LogType.DRIVER, Level.ALL);
        loggingPreferences.enable(LogType.PERFORMANCE, Level.ALL);
        loggingPreferences.enable(LogType.PROFILER, Level.ALL);
        loggingPreferences.enable(LogType.SERVER, Level.ALL);
    }

    public void setWait () {
        wait = new WebDriverWait(driver, (generalSettings.getWait().getTimeouts().getMax()/1000));
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public WebDriver getDriver () {
        return driver;
    }

    public void setDriver (WebDriver driver) {
        this.driver = new EventFiringWebDriver(driver).register(new WebDriverEventHandler());
        setWindowSize();
    }

    private void setWindowSize() {
        String windowSize = ConfigManager.getDriverConfigData().getGeneralSettings().getWindowSize().toUpperCase();
        if (windowSize.equals(WindowSize.MAXIMIZED.toString())) {
            driver.manage().window().maximize();
        }
    }

    private void checkCustomTargetPath() {
        String customTargetPath = generalSettings.getCustomTargetPath();

        if (customTargetPath != null && !customTargetPath.equals("")) {
            System.setProperty("wdm.targetPath", customTargetPath);
        }
    }

    abstract public WebDriver init ();
    abstract public void setOptions ();
    abstract public Object getOptions ();

    public LoggingPreferences getLoggingPreferences() {
        return loggingPreferences;
    }
}
