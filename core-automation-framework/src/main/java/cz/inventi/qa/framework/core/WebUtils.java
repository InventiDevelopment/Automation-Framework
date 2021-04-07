package cz.inventi.qa.framework.core;

import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WebUtils {
    private final AppInstance appInstance;
    private final WebDriver webDriver;

    public WebUtils(AppInstance appInstance) {
        this.appInstance = appInstance;
        webDriver = appInstance.getWebDriverManager().getDriver();
    }

    public void waitFor (int millis) {
        try {
            appInstance.getWebDriverManager().getDriver().wait(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getFilePathDecoded(String filePath) {
        return URLDecoder.decode(filePath, StandardCharsets.UTF_8);
    }

    public void waitUntilDocumentReady() {
        Log.debug("Waiting for document load and JS actions to finish");

        new FluentWait<>(webDriver)
                .withTimeout(Duration.ofMillis(appInstance.getConfigManager().getWebDriverConfigData().getTimeouts().getMid()))
                .pollingEvery(Duration.ofMillis(100))
                .withMessage("JavaScript operations still not finished - document not ready")
                .until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        Log.debug("Document finished loading");
    }
}
