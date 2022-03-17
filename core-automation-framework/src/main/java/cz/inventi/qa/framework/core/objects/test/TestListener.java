package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * TestNG listener to add method parameters into
 * TestSuiteParameters object.
 */
public class TestListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        IInvokedMethodListener.super.beforeInvocation(method, testResult);
        TestSuiteParameters.parseTestMethodParameters(context, method);
    }
}
