package cz.inventi.qa.framework.core.objects.parameters;

import cz.inventi.qa.framework.core.objects.framework.Log;
import org.testng.IInvokedMethod;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class dedicated primarily to hold all the current TestNG parameters.
 * Should be thread-safe singleton class shared during the run.
 */
public class TestSuiteParameters {
    private static TestSuiteParameters testSuiteParameters;
    private final Map<String, String> parameters;

    public TestSuiteParameters() {
        this.parameters = new HashMap<>();
    }

    /**
     * Returns or creates a singleton instance of TestSuiteParameters.
     * @return TestSuiteParameters instance
     */
    public static synchronized TestSuiteParameters getInstance() {
        if (testSuiteParameters == null) testSuiteParameters = new TestSuiteParameters();
        return testSuiteParameters;
    }

    /**
     * Builds regex pattern that will match values of parameters
     * that should be masked.
     * @return Regex with parameter values
     */
    public static String getMaskedValuesRegex() {
        StringBuilder maskedValuesRegex = new StringBuilder();
        for (String paramName : getParameters().keySet()) {
            Pattern sensitiveNameReg = Pattern.compile("(password|secret)", Pattern.CASE_INSENSITIVE);
            Matcher isSensitive = sensitiveNameReg.matcher(paramName);
            if (isSensitive.find()) {
                if (!maskedValuesRegex.toString().equals("")) maskedValuesRegex.append("|");
                String paramValue = getParameter(paramName);
                if (paramValue != null) {
                    Pattern regexEscChars = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
                    maskedValuesRegex.append(regexEscChars.matcher(paramValue).replaceAll("\\\\$0"));
                }
            }
        }
        return maskedValuesRegex.toString();
    }

    /**
     * Sets test suite parameters globally.
     * @param parameters Test suite parameters
     */
    public static synchronized void setParameters(Map<String, String> parameters) {
        getParameters().clear();
        getParameters().putAll(parameters);
        String maskedValuesRegex = getMaskedValuesRegex();
        String parametersOutput = parameters.toString();
        if (maskedValuesRegex.length() > 0) {
            parametersOutput = parametersOutput.replaceAll(maskedValuesRegex, "[**MASKED**]");
        }
        Log.debug("Mapping test suite parameters (" + parameters.size() + "): " + parametersOutput);
    }

    public static Map<String, String> getParameters() {
        return getInstance().parameters;
    }

    public static String getParameter(String parameterName) {
        String paramValue = getParameters().get(parameterName);
        if (paramValue == null) paramValue = getParameters().get(parameterName.toLowerCase());
        return paramValue;
    }

    public static String getParameter(String parameterName, String appName) {
        if (getParameter(parameterName) == null) {
            return getParameter(parameterName + ":" + appName);
        } else {
            return getParameter(parameterName);
        }
    }

    public static void parseTestSuiteParameters(ITestContext context) {
        XmlTest currentTest = context.getCurrentXmlTest();
        Map<String, String> parameters = currentTest.getAllParameters();
        setParameters(replaceValuesFromCommandLine(parameters, currentTest));
    }

    public static void parseTestMethodParameters(ITestContext context, IInvokedMethod method) {
        ITestNGMethod foundTestMethod = Arrays
                .stream(context.getAllTestMethods())
                .filter(testMethod -> testMethod.getMethodName().equals(method.getTestMethod().getMethodName()))
                .findFirst()
                .orElse(null);
        if (foundTestMethod != null) {
            XmlTest currentTest = context.getCurrentXmlTest();
            Map<String, String> parameters = foundTestMethod.findMethodParameters(currentTest);
            setParameters(replaceValuesFromCommandLine(parameters, currentTest));
        }
    }

    private static Map<String, String> replaceValuesFromCommandLine(
            Map<String, String> parameters,
            XmlTest currentTest
    ) {
        for (String key : parameters.keySet()) {
            String parameterValue = parameters.get(key);
            if (parameterValue.startsWith("$")) {
                String newValue = currentTest.getParameter(parameterValue.substring(1));
                parameters.put(key, newValue);
            }
        }
        return parameters;
    }
}