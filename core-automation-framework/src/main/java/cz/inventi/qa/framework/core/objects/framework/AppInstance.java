package cz.inventi.qa.framework.core.objects.framework;

import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.data.enums.api.ApiMandatoryParameters;
import cz.inventi.qa.framework.core.data.enums.web.WebMandatoryParameters;
import cz.inventi.qa.framework.core.factories.api.ApiObjectFactory;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.managers.*;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.framework.core.utils.ApiUtils;
import cz.inventi.qa.framework.core.utils.WebUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppInstance {
    private final TestVariablesManager testVariablesManager;
    private final ConfigManager configManager;
    private final LanguageManager languageManager;
    private final WebDriverManager webDriverManager;
    private final ReportManager reportManager;
    private ApplicationType applicationType;
    private String applicationName;
    private WebUtils webUtils;
    private ApiUtils apiUtils;

    public AppInstance(ApplicationType applicationType, String applicationName) {
        setBasicAppInformation(applicationName, applicationType);
        checkMandatoryParametersAreSet();
        testVariablesManager = new TestVariablesManager();
        configManager = new ConfigManager(this);
        webDriverManager = new WebDriverManager(this);
        languageManager = LanguageManager.getInstance();
        reportManager = ReportManager.getInstance();
    }

    private void checkMandatoryParametersAreSet() {
        Class<? extends Enum<?>> mandatoryParamsEnum;
        switch (applicationType) {
            case API:
                mandatoryParamsEnum = ApiMandatoryParameters.class;
            case WEB:
                mandatoryParamsEnum = WebMandatoryParameters.class;
                break;
            default:
                throw new RuntimeException("Could not find mandatory parameters for given application type");
        }

        List<String> mandatoryParameters = Stream
                .of(mandatoryParamsEnum.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (String mandatoryParameter : mandatoryParameters) {
            if (TestSuiteParameters.getParameters() == null || TestSuiteParameters.getParameter(mandatoryParameter.toLowerCase()) == null) {
                throw new RuntimeException("Parameter '" + mandatoryParameter.toLowerCase() + "' has to be supplied " +
                        "either in the TestNG suite or as a Maven parameter to start this type of application");
            }
        }
    }

    public <T extends WebPage> T initWebPage(Class<T> startingWebPage) {
        webDriverManager.init();
        return WebObjectFactory.initPage(startingWebPage, this);
    }

    public <T extends Api> T initApi(Class<T> api) {
        return ApiObjectFactory.initApi(api, this);
    }

    public WebDriverManager getWebDriverManager() {
        return webDriverManager;
    }

    public TestVariablesManager getTestVariablesManager() {
        return testVariablesManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public WebUtils getWebUtils() {
        return webUtils;
    }

    public ReportManager getReportManager() {
        return reportManager;
    }

    public ApiUtils getApiUtils() {
        return apiUtils;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public String getApplicationName() {
        return applicationName;
    }

    private void setBasicAppInformation(String applicationName, ApplicationType applicationType) {
        Log.debug("Initializing new '" + applicationType + "' application '" + applicationName + "'");
        this.applicationType = applicationType;
        this.applicationName = applicationName;
    }

    /**
     * Ends all running state-dependent instances.
     */
    public void quit() {
        quitWebDriver();
    }

    private void quitWebDriver() {
        if (ApplicationType.WEB.equals(getApplicationType())) {
            webDriverManager.getDriver().quit();
        }
    }
}