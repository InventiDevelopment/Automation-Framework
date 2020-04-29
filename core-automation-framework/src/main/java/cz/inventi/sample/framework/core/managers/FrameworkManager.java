package cz.inventi.sample.framework.core.managers;

import cz.inventi.sample.framework.core.Log;
import cz.inventi.sample.framework.core.data.enums.Language;
import cz.inventi.sample.framework.core.objects.web.WebPage;
import cz.inventi.sample.framework.core.factories.webobject.WebObjectFactory;

public class FrameworkManager {

    public static <T extends WebPage> T initWebApp (String browser, String environment, Class<T> startingWebPage) {
        return initWebApp(browser, environment, Language.EN.toString(), startingWebPage);
    }

    public static <T extends WebPage> T initWebApp (String browser, String environment, String language, Class<T> startingWebPage) {
        setParameters(browser, environment, language);
        ConfigManager.init();
        LanguageManager.init();
        DriverManager.init();
        AppManager.initApplication(startingWebPage);
        return WebObjectFactory.initPage(startingWebPage);
    }

    private static void setParameters(String browser, String environment, String language) {
        ParametersManager.setBrowser(browser);
        ParametersManager.setLanguage(language);
        ParametersManager.setEnvironment(environment);
        Log.debug("Launching framework with parameters:\n- Browser: '" + ParametersManager.getBrowser() + "'\n- Language: '"
                + ParametersManager.getLanguage() + "'\n- Environment: '" + ParametersManager.getEnvironment() + "'\n");
    }
}
