package cz.inventi.qa.framework.core.webdrivers;

import cz.inventi.qa.framework.core.managers.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeWebDriver extends DriverWrapper {
    private ChromeOptions options;

    public ChromeWebDriver() {
        setOptions();
        setDriver(init());
    }

    @Override
    public WebDriver init() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver(options);
    }

    @Override
    public void setOptions() {
        options = new ChromeOptions()
            .addArguments(
                ConfigManager
                    .getDriverConfigData()
                    .getChrome()
                    .getArguments()
            );

        options.setCapability(CapabilityType.LOGGING_PREFS, getLoggingPreferences());
    }

    @Override
    public ChromeOptions getOptions() {
        return options;
    }
}
