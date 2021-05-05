package cz.inventi.qa.framework.tests.core;

import cz.inventi.qa.framework.core.objects.test.BaseTest;
import cz.inventi.qa.framework.testapi.JsonPlaceHolderApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class ApiTestCase extends BaseTest {
    public JsonPlaceHolderApi jsonPlaceHolderApi;

    @BeforeClass
    @Parameters({"environment"})
    public void init(@Optional("PROD") String environment) {
        jsonPlaceHolderApi = initApiAppInstance(environment, JsonPlaceHolderApi.class);
    }
}
