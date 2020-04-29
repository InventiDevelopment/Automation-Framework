package cz.inventi.sample.framework.core.webdrivers;

import cz.inventi.sample.framework.core.managers.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

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
    }

    @Override
    public ChromeOptions getOptions() {
        return options;
    }
}
