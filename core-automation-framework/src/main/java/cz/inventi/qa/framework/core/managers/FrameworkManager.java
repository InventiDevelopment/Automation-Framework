package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.factories.api.ApiObjectFactory;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.web.WebPage;

public class FrameworkManager {

    public static <T extends WebPage> T initWebApp(String browser, String environment, Class<T> startingWebPage) {
        return initWebApp(browser, environment, Language.NONE.toString(), startingWebPage);
    }

    public static <T extends WebPage> T initWebApp(String browser, String environment, String language, Class<T> startingWebPage) {
        ParametersManager.setWebParameters(browser, environment, language, startingWebPage);
        ConfigManager.initWebConfigs();
        LanguageManager.init();
        AppManager.initWebApplication(startingWebPage);
        return WebObjectFactory.initPage(startingWebPage);
    }

    public static <T extends Api> T initApiApp(String environment, Class<T> api) {
        ParametersManager.setApiParameters(environment, api);
        ConfigManager.initApiConfigs();
        AppManager.initApiApplication(api);
        return ApiObjectFactory.initApi(api);
    }
}
