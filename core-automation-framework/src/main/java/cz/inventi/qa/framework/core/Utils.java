package cz.inventi.qa.framework.core;

import cz.inventi.qa.framework.core.managers.DriverManager;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Utils {
    public static void waitFor (int millis) {
        try {
            DriverManager.getDriver().wait(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getFilePathDecoded(String filePath) {
        return URLDecoder.decode(filePath, StandardCharsets.UTF_8);
    }
}
