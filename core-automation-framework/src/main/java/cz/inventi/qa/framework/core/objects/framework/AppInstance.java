package cz.inventi.qa.framework.core.objects.framework;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.data.enums.api.ApiMandatoryParameters;
import cz.inventi.qa.framework.core.data.enums.web.WebMandatoryParameters;
import cz.inventi.qa.framework.core.factories.api.ApiObjectFactory;
import cz.inventi.qa.framework.core.factories.web.webobject.WebObjectFactory;
import cz.inventi.qa.framework.core.managers.*;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.api.RestAssuredManager;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppInstance {
    private final TestVariablesManager testVariablesManager;
    private final ConfigManager configManager;
    private final LanguageManager languageManager;
    private final WebDriverManager webDriverManager;
    private final RestAssuredManager restAssuredManager;
    private final ReportManager reportManager;
    private ApplicationType applicationType;
    private String applicationName;

    public AppInstance(ApplicationType applicationType, String applicationName) {
        setBasicAppInformation(applicationName, applicationType);
        checkMandatoryParametersAreSet();
        testVariablesManager = new TestVariablesManager();
        configManager = new ConfigManager(this);
        webDriverManager = new WebDriverManager(this);
        languageManager = LanguageManager.getInstance();
        reportManager = ReportManager.getInstance();
        restAssuredManager = new RestAssuredManager(this);
    }

    private void checkMandatoryParametersAreSet() {
        Class<? extends Enum<?>> mandatoryParamsEnum = switch (applicationType) {
            case API -> ApiMandatoryParameters.class;
            case WEB -> WebMandatoryParameters.class;
            default -> throw new FrameworkException("Could not find mandatory parameters for given application type");
        };

        List<String> mandatoryParameters = Stream
                .of(mandatoryParamsEnum.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (String mandatoryParameter : mandatoryParameters) {
            if (TestSuiteParameters.getParameters() == null || TestSuiteParameters.getParameter(mandatoryParameter.toLowerCase()) == null) {
                throw new FrameworkException("Parameter '" + mandatoryParameter.toLowerCase() + "' has to be supplied " +
                        "either in the TestNG suite or as a Maven parameter to start this type of application");
            }
        }
    }

    public <T extends WebPage> T initWebPage(Class<T> startingWebPage) {
        webDriverManager.init();
        return WebObjectFactory.initPage(startingWebPage, this);
    }

    public <T extends Api> T initApi(Class<T> api) {
        setRestAssuredConfiguration();
        return ApiObjectFactory.initApi(api, this);
    }

    private void setRestAssuredConfiguration() {
        /* Set Jackson mapping options for Rest Assured */
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                new ObjectMapperConfig().jackson2ObjectMapperFactory(
                    (cls, charset) ->
                        new ObjectMapper().findAndRegisterModules()
                            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                )
        );
        /* Set logging for debug mode */
        if (FrameworkManager.getRunMode().equals(RunMode.DEBUG)) {
            RestAssured.requestSpecification = RestAssured.requestSpecification
                    .log()
                    .all()
                    .request();
        }
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

    public ReportManager getReportManager() {
        return reportManager;
    }

    public ApplicationType getApplicationType() {
        return applicationType;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public RestAssuredManager getRestAssuredManager() {
        return restAssuredManager;
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