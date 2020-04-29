package cz.inventi.sample.framework.core.managers;

import cz.inventi.sample.framework.core.Log;
import cz.inventi.sample.framework.core.data.enums.Browser;
import cz.inventi.sample.framework.core.data.enums.Language;

public class ParametersManager {
    private static String environment;
    private static Browser browser;
    private static Language language;

    public static String getEnvironment() {
        return environment;
    }

    public static void setEnvironment(String environment) {
        ParametersManager.environment = environment;
    }

    public static Browser getBrowser() {
        return browser;
    }

    public static Language getLanguage() {
        return language;
    }

    public static void setBrowser(Browser browser) {
        ParametersManager.browser = browser;
    }

    public static void setLanguage(Language language) {
        ParametersManager.language = language;
    }

    public static void setBrowser(String browser) {
        try {
            setBrowser(Browser.valueOf(browser.toUpperCase()));
        } catch (IllegalArgumentException e) {
            Log.fail("The '" + browser + "' browser is not supported.");
        }
    }

    public static void setLanguage(String language) {
        try {
            setLanguage(Language.valueOf(language.toUpperCase()));
        } catch (IllegalArgumentException e) {
            Log.fail("The '" + language + "' language is not supported.");
        }
    }
}
