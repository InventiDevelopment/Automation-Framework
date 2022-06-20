package cz.inventi.qa.framework.core.objects.variables;

import cz.inventi.qa.framework.core.data.enums.web.WebMandatoryParameters;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

public class AppVariables {
    private final String applicationName;

    public AppVariables(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public String getEnvironment() {
        return TestSuiteParameters.getParameter(
                WebMandatoryParameters.ENVIRONMENT.name().toLowerCase(),
                applicationName
        );
    }
}
