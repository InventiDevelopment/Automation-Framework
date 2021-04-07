package cz.inventi.qa.framework.core.objects.framework;

import cz.inventi.qa.framework.core.WebUtils;
import cz.inventi.qa.framework.core.factories.api.ApiObjectFactory;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.managers.*;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.web.WebPage;

public class AppInstance {
    private final ParametersManager parametersManager;
    private final ConfigManager configManager;
    private final LanguageManager languageManager;
    private final AppManager appManager;
    private final WebDriverManager webDriverManager;
    private final WebUtils webUtils;

    public AppInstance() {
        this.parametersManager = new ParametersManager(this);
        this.configManager = new ConfigManager(this);
        this.languageManager = new LanguageManager(this);
        this.appManager = new AppManager(this);
        this.webDriverManager = new WebDriverManager(this);
        this.webUtils = new WebUtils(this);
    }

    public <T extends WebPage> T initWebApp(String browser, String environment, String language, Class<T> startingWebPage) {
        parametersManager.setWebParameters(browser, environment, language, startingWebPage);
        configManager.initWebConfigs();
        languageManager.init();
        appManager.initWebApplication(startingWebPage);
        webDriverManager.init();
        return WebObjectFactory.initPage(startingWebPage, this);
    }

    public <T extends Api> T initApiApp(String environment, Class<T> api) {
        parametersManager.setApiParameters(environment,api);
        configManager.initApiConfigs();
        appManager.initApiApplication(api);
        return ApiObjectFactory.initApi(api, this);
    }

    public WebDriverManager getWebDriverManager() {
        return webDriverManager;
    }

    public ParametersManager getParametersManager() {
        return parametersManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public AppManager getAppManager() {
        return appManager;
    }

    public WebUtils getWebUtils() {
        return webUtils;
    }
}
