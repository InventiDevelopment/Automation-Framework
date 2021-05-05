package cz.inventi.qa.framework.core.objects.parameters;

import cz.inventi.qa.framework.core.Log;

import java.util.Map;

/**
 * Class dedicated primarily to hold all the current TestNG parameters.
 * Should be thread-safe singleton class.
 */
public class TestSuiteParameters {
    private static Map<String, String> parameters;

    private static class Holder {
        private static final TestSuiteParameters INSTANCE = new TestSuiteParameters();
    }

    public static TestSuiteParameters getInstance() {
        return Holder.INSTANCE;
    }

    public static void setParameters(Map<String, String> parameters) {
        Log.info("Mapping test suite parameters: " + parameters);
        TestSuiteParameters.parameters = parameters;
    }

    public static Map<String, String> getParameters() {
        return parameters;
    }

    public static String getParameter(String parameterName) {
        return parameters.get(parameterName);
    }
}
