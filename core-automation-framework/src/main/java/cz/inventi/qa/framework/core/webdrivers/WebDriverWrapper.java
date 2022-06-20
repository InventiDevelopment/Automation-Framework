package cz.inventi.qa.framework.core.webdrivers;

import cz.inventi.qa.framework.core.data.enums.web.WindowSizeType;
import cz.inventi.qa.framework.core.data.web.GeneralSettings;
import cz.inventi.qa.framework.core.data.web.WindowSizeDimensions;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;

public abstract class WebDriverWrapper {
    private final AppInstance appInstance;
    private final GeneralSettings generalSettings;
    private WebDriver driver;
    private WebDriverWait wait;
    private LoggingPreferences loggingPreferences;

    public WebDriverWrapper(AppInstance appInstance) {
        this.appInstance = appInstance;
        generalSettings = appInstance.getConfigManager().getWebDriverConfigData().getGeneralSettings();
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
        GeneralSettings generalSettings = appInstance.getConfigManager().getWebDriverConfigData().getGeneralSettings();
        WindowSizeType windowSizeType = generalSettings.getWindowSize().getSizeType();
        WindowSizeDimensions windowSizeDimensions = generalSettings.getWindowSize().getDimensions();

        if (windowSizeType != null) {
            if (WindowSizeType.MAXIMIZED.equals(windowSizeType)) {
                driver.manage().window().maximize();
            }
        } else if (windowSizeDimensions != null) {
            driver.manage().window().setSize(new Dimension(windowSizeDimensions.getWidth(), windowSizeDimensions.getHeight()));
        }
    }

    private void checkCustomTargetPath() {
        String customTargetPath = generalSettings.getCustomTargetPath();

        if (customTargetPath != null && !customTargetPath.equals("")) {
            System.setProperty("wdm.targetPath", customTargetPath);
        }
    }

    public LoggingPreferences getLoggingPreferences() {
        return loggingPreferences;
    }

    public AppInstance getAppInstance() {
        return appInstance;
    }

    abstract public WebDriver init ();
    abstract public void setOptions ();
    abstract public Object getOptions ();
}
