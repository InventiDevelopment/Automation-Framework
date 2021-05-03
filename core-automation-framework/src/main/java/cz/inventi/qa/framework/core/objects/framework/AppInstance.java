package cz.inventi.qa.framework.core.objects.framework;

import cz.inventi.qa.framework.core.ApiUtils;
import cz.inventi.qa.framework.core.WebUtils;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
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
    private final ReportManager reportManager;
    private ApplicationType applicationType;
    private WebUtils webUtils;
    private ApiUtils apiUtils;

    public AppInstance() {
        parametersManager = new ParametersManager(this);
        configManager = new ConfigManager(this);
        languageManager = new LanguageManager(this);
        appManager = new AppManager(this);
        webDriverManager = new WebDriverManager(this);
        reportManager = new ReportManager(this);
    }

    public <T extends WebPage> T initWebApp(String browser, String environment, String language, Class<T> startingWebPage) {
        parametersManager.setWebParameters(browser, environment, language, startingWebPage);
        configManager.initWebConfigs();
        languageManager.init(language);
        appManager.initWebApplication(startingWebPage);
        webDriverManager.init();
        reportManager.init();
        applicationType = ApplicationType.WEB;
        return WebObjectFactory.initPage(startingWebPage, this);
    }

    public <T extends Api> T initApiApp(String environment, Class<T> api) {
        parametersManager.setApiParameters(environment,api);
        configManager.initApiConfigs();
        appManager.initApiApplication(api);
        reportManager.init();
        applicationType = ApplicationType.API;
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

    public ApiUtils getApiUtils() {
        return apiUtils;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }
}
