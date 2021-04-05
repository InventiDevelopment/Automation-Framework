package cz.inventi.qa.framework.core;

import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class Utils {
    public static void waitFor (int millis) {
        try {
            WebDriverManager.getDriver().wait(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePathDecoded(String filePath) {
        return URLDecoder.decode(filePath, StandardCharsets.UTF_8);
    }

    public static void waitUntilDocumentReady() {
        WebDriver driver = WebDriverManager.getDriver();
        Log.debug("Waiting for document load and JS actions to finish");

        new FluentWait<>(driver)
                .withTimeout(Duration.ofMillis(ConfigManager.getWebDriverConfigData().getTimeouts().getMid()))
                .pollingEvery(Duration.ofMillis(100))
                .withMessage("JavaScript operations still not finished - document not ready")
                .until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));

        Log.debug("Document finished loading");
    }
}
