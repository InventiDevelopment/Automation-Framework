package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

public class TestRun {
    private final Map<String, AppInstance> appInstances;
    private final SoftAssert softAssert;

    public TestRun() {
        this.softAssert = new SoftAssert();
        this.appInstances = new HashMap<>();
    }

    public Map<String, AppInstance> getAppInstances() {
        return appInstances;
    }

    public SoftAssert getSoftAssert() {
        return softAssert;
    }
}