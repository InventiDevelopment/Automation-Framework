package cz.inventi.qa.framework.core.utils;

import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.web.WebAppInstance;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.FluentWait;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WebUtils {

    public static void waitFor(int millis, WebAppInstance<?> appInstance) {
        try {
            appInstance.getWebDriverManager().getDriver().wait(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePathDecoded(String filePath) {
        return URLDecoder.decode(filePath, StandardCharsets.UTF_8);
    }

    public static void waitUntilDocumentReady(WebAppInstance<?> appInstance) {
        Log.debug("Waiting for document load and JS actions to finish");
        int midWait = appInstance.getConfigManager().getWebDriverConfigData().getTimeouts().getMid();
        new FluentWait<>(appInstance.getWebDriverManager().getDriver())
                .withTimeout(Duration.ofMillis(midWait))
                .pollingEvery(Duration.ofMillis(100))
                .withMessage("JavaScript operations still not finished - document not ready")
                .until(webDriver ->
                        ((JavascriptExecutor) webDriver)
                                .executeScript("return document.readyState")
                                .equals("complete")
                );
        Log.debug("Document finished loading");
    }
}
