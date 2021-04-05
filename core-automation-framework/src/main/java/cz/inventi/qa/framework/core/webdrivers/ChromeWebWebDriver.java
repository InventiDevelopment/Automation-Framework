package cz.inventi.qa.framework.core.webdrivers;

import cz.inventi.qa.framework.core.managers.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

public class ChromeWebWebDriver extends WebDriverWrapper {
    private ChromeOptions options;

    public ChromeWebWebDriver() {
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
                    .getWebDriverConfigData()
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
