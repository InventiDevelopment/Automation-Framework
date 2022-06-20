package cz.inventi.qa.framework.tests.framework.parameters;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.ParametersTestSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Framework Unit Tests")
@Story("Test Parameters Capabilities of the Framework")
public class ParametersTests extends TestBase {
    private ParametersTestSteps parametersTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        parametersTestSteps = new ParametersTestSteps();
    }

    @Test(description = "Check Test Suite Parameters Are not Null")
    public void checkTestSuiteParametersAreNotNull() {
        parametersTestSteps.checkTestSuiteParametersAreNotNull();
    }

    @Test(description = "Retrieve Specific Application Parameter")
    public void retrieveSpecificAppParameter() {
        parametersTestSteps.retrieveSpecificApplicationParameter(
                "testweb",
                "appspecificparam",
                "appspecificparamvaltestweb"
        );
        parametersTestSteps.retrieveSpecificApplicationParameter(
                "jsonplaceholder",
                "appspecificparam",
                "appspecificparamvaljsonplaceholder"
        );
    }

    @Test(description = "Retrieve Shared Application Parameter")
    public void retrieveSharedAppParameter() {
        parametersTestSteps.retrieveSharedApplicationParameter(
                "sharedparam",
                "sharedparamval"
        );
    }

    @Test(description = "Retrieve Current Application Parameter")
    public void retrieveCurrentAppParameter() {
        parametersTestSteps.retrieveCurrentApplicationParameter(
            "environment",
                "prod"
        );
    }
}