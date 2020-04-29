package cz.inventi.sample.framework.core.webdrivers;

import cz.inventi.sample.framework.core.data.web.GeneralSettings;
import cz.inventi.sample.framework.core.managers.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class DriverWrapper {
    private WebDriver driver;
    private WebDriverWait wait;
    private GeneralSettings generalSettings;

    public DriverWrapper() {
        generalSettings = ConfigManager.getDriverConfigData().getGeneralSettings();
        checkCustomTargetPath();
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
        this.driver = driver;
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
}
