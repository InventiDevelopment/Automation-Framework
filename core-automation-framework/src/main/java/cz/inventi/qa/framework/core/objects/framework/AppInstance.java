package cz.inventi.qa.framework.core.objects.framework;

import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.managers.ConfigManager;
import cz.inventi.qa.framework.core.managers.LanguageManager;
import cz.inventi.qa.framework.core.managers.ReportManager;
import cz.inventi.qa.framework.core.managers.TestVariablesManager;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AppInstance<T> {
    private final TestVariablesManager testVariablesManager;
    private final ConfigManager configManager;
    private final LanguageManager languageManager;
    private final ReportManager reportManager;
    private ApplicationType applicationType;
    private String applicationName;
    private T applicationStartingClass;

    public AppInstance(ApplicationType applicationType, String applicationName) {
        setBasicAppInformation(applicationName, applicationType);
        testVariablesManager = new TestVariablesManager();
        configManager = new ConfigManager(this);
        languageManager = LanguageManager.getInstance();
        reportManager = ReportManager.getInstance();
    }

    public void checkMandatoryParametersAreSet(Class<? extends Enum<?>> mandatoryParamsEnum) {
        List<String> mandatoryParameters = Stream
                .of(mandatoryParamsEnum.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (String mandatoryParameter : mandatoryParameters) {
            if (
                    TestSuiteParameters.getParameters() == null ||
                    TestSuiteParameters.getParameter(mandatoryParameter.toLowerCase()) == null
            ) {
                throw new FrameworkException("Parameter '" + mandatoryParameter.toLowerCase() + "' has to be" +
                        " supplied either in the TestNG suite or as a Maven parameter to start this type of" +
                        " application");
            }
        }
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

    public T getApplicationStartingClass() {
        return applicationStartingClass;
    }

    private void setBasicAppInformation(String applicationName, ApplicationType applicationType) {
        Log.debug("Initializing new '" + applicationType + "' application '" + applicationName + "'");
        this.applicationType = applicationType;
        this.applicationName = applicationName;
    }

    public void setApplicationStartingClass(T applicationStartingClass) {
        this.applicationStartingClass = applicationStartingClass;
    }

    abstract public void quit();
}