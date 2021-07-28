package cz.inventi.qa.framework.core.objects.parameters;

import cz.inventi.qa.framework.core.objects.framework.Log;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class dedicated primarily to hold all the current TestNG parameters.
 * Should be thread-safe singleton class shared during the run.
 */
public class TestSuiteParameters {
    private static Map<String, String> parameters;
    private static TestSuiteParameters testSuiteParameters;

    /**
     * Returns or creates a singleton instance of TestSuiteParameters.
     * @return TestSuiteParameters instance
     */
    public static synchronized TestSuiteParameters getInstance() {
        if (testSuiteParameters == null)  testSuiteParameters = new TestSuiteParameters();
        return testSuiteParameters;
    }

    /**
     * Builds regex pattern that will match values of parameters
     * that should be masked.
     * @return Regex with parameter values
     */
    public static String getMaskedValuesRegex() {
        StringBuilder maskedValuesRegex = new StringBuilder();
        for (String paramName : TestSuiteParameters.getParameters().keySet()) {
            Pattern sensitiveNameReg = Pattern.compile("(password|secret)", Pattern.CASE_INSENSITIVE);
            Matcher isSensitive = sensitiveNameReg.matcher(paramName);
            if (isSensitive.find()) {
                if (!maskedValuesRegex.toString().equals("")) maskedValuesRegex.append("|");
                String paramValue = TestSuiteParameters.getParameters().get(paramName);
                Pattern regexEscChars = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
                maskedValuesRegex.append(regexEscChars.matcher(paramValue).replaceAll("\\\\$0"));
            }
        }
        return maskedValuesRegex.toString();
    }

    /**
     * Sets test suite parameters globally.
     * @param parameters Test suite parameters
     */
    public static void setParameters(Map<String, String> parameters) {
        TestSuiteParameters.parameters = parameters;
        String maskedValuesRegex = getMaskedValuesRegex();
        String parametersOutput = parameters.toString();
        if (maskedValuesRegex.length() > 0) {
            parametersOutput = parametersOutput.replaceAll(maskedValuesRegex, "[**MASKED**]");
        }
        Log.info("Mapping test suite parameters (" + parameters.size() + "): " + parametersOutput);
    }

    public static Map<String, String> getParameters() {
        return parameters;
    }

    public static String getParameter(String parameterName) {
        return parameters.get(parameterName);
    }
}