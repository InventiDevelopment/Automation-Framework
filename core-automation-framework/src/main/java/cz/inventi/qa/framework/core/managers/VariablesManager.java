package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Class allowing setting variables bound to given instance.
 */
public class VariablesManager {
    private final Map<String, Object> variables;

    public VariablesManager() {
        variables = new HashMap<>();
    }

    public TestSuiteParameters getTestSuiteParameters() {
        return TestSuiteParameters.getInstance();
    }

    public Map<String, Object> getVariables() {
        return variables;
    }
}