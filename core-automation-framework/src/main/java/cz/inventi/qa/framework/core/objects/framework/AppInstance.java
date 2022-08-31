package cz.inventi.qa.framework.core.objects.framework;

import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.managers.AppVariablesManager;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.LanguageManager;
import cz.inventi.qa.framework.core.managers.ReportManager;
import cz.inventi.qa.framework.core.objects.api.Endpoint;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.web.WebPage;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AppInstance<T> {
    private final AppVariablesManager testVariablesManager;
    private final ConfigManager configManager;
    private final LanguageManager languageManager;
    private final ReportManager reportManager;
    private final Class<T> applicationStartingClass;
    private ApplicationType applicationType;
    private String applicationName;
    private T applicationStartingClassInitialized;

    public AppInstance(Class<T> applicationStartingClass, String applicationName) {
        setBasicAppInformation(applicationName, getApplicationType(applicationStartingClass));
        this.applicationStartingClass = applicationStartingClass;
        testVariablesManager = new AppVariablesManager(applicationName);
        configManager = new ConfigManager(this);
        languageManager = new LanguageManager(applicationName);
        reportManager = ReportManager.getInstance();
    }

    public void checkMandatoryParametersAreSet(Class<? extends Enum<?>> mandatoryParamsEnum) {
        List<String> mandatoryParameters = Stream
                .of(mandatoryParamsEnum.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
        for (String mandatoryParameter : mandatoryParameters) {
            if (!checkMandatoryParameterDefined(mandatoryParameter)) {
                throw new FrameworkException(
                        "Parameter '" + mandatoryParameter.toLowerCase() + "' for application '" + applicationName +
                        "' has to be supplied either in the TestNG suite or as a Maven parameter to start this " +
                        "type of application in appropriate format of '" + mandatoryParameter.toLowerCase() +
                        "' or '" + mandatoryParameter.toLowerCase() + ":" + applicationName + "'."
                );
            }
        }
    }

    private boolean checkMandatoryParameterDefined(String mandatoryParameter) {
        String mandatoryParamForApp = mandatoryParameter.toLowerCase() + ":" + applicationName;
        if (TestSuiteParameters.getParameters() == null) return false;
        return TestSuiteParameters.getParameter(mandatoryParameter.toLowerCase()) != null ||
               TestSuiteParameters.getParameter(mandatoryParamForApp) != null;
    }

    public AppVariablesManager getTestVariablesManager() {
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

    public T getApplicationStartingClassInitialized() {
        return applicationStartingClassInitialized;
    }

    public Class<T> getApplicationStartingClass() {
        return applicationStartingClass;
    }

    private void setBasicAppInformation(String applicationName, ApplicationType applicationType) {
        Log.debug("Initializing new '" + applicationType + "' application '" + applicationName + "'");
        this.applicationType = applicationType;
        this.applicationName = applicationName;
    }

    public void setApplicationStartingClassInitialized(T applicationStartingClassInitialized) {
        this.applicationStartingClassInitialized = applicationStartingClassInitialized;
    }

    public ApplicationType getApplicationType(Class<?> startingAppClass) {
        if (Endpoint.class.isAssignableFrom(startingAppClass)) return ApplicationType.API;
        if (WebPage.class.isAssignableFrom(startingAppClass)) return ApplicationType.WEB;
        return null;
    }

    abstract public void quit();
}