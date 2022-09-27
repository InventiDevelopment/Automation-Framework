package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.managers.VariablesManager;
import cz.inventi.qa.framework.core.objects.api.ApiAppInstance;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.test.assertions.SoftAssertCollector;
import cz.inventi.qa.framework.core.objects.web.WebAppInstance;
import cz.inventi.qa.framework.core.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class TestRun {
    private final VariablesManager testRunVariablesManager;
    private final Map<String, WebAppInstance<?>> webAppInstances;
    private final Map<String, ApiAppInstance<?>> apiAppInstances;
    private final SoftAssertCollector softAssertCollector;

    public TestRun() {
        this.softAssertCollector = new SoftAssertCollector();
        this.webAppInstances = new HashMap<>();
        this.apiAppInstances = new HashMap<>();
        this.testRunVariablesManager = new VariablesManager();
    }

    public Map<String, WebAppInstance<?>> getWebAppInstances() {
        return webAppInstances;
    }

    public Map<String, ApiAppInstance<?>> getApiAppInstances() {
        return apiAppInstances;
    }

    public SoftAssertCollector getSoftAssertCollector() {
        return softAssertCollector;
    }

    public VariablesManager getTestRunVariablesManager() {
        return testRunVariablesManager;
    }

    public void quitWebAppInstance(String appName) {
        webAppInstances.get(appName).quit();
        webAppInstances.remove(appName);
    }

    public void quitApiAppInstance(String appName) {
        apiAppInstances.get(appName).quit();
        apiAppInstances.remove(appName);
    }

    public void quitAppInstances() {
        webAppInstances.forEach((s, webAppInstance) -> webAppInstance.quit());
        apiAppInstances.forEach((s, apiAppInstance) -> apiAppInstance.quit());
        webAppInstances.clear();
        apiAppInstances.clear();
    }

    public void quit() {
        Log.info("Quitting all AppInstances created by test thread (" + Utils.getTestIdentifier() + ")");
        quitAppInstances();
        Log.info("All AppInstances were quit for test thread (" + Utils.getTestIdentifier() + ")");
    }
}