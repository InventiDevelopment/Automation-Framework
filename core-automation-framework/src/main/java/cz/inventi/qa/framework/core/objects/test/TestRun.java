package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.test.assertions.SoftAssertCollector;

import java.util.HashMap;
import java.util.Map;

public class TestRun {
    private final Map<String, AppInstance> appInstances;
    private final SoftAssertCollector softAssertCollector;

    public TestRun() {
        this.softAssertCollector = new SoftAssertCollector();
        this.appInstances = new HashMap<>();
    }

    public Map<String, AppInstance> getAppInstances() {
        return appInstances;
    }

    public SoftAssertCollector getSoftAssertCollector() {
        return softAssertCollector;
    }
}