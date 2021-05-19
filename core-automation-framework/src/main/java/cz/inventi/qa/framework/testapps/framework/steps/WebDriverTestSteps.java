package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.objects.test.steps.StepsBase;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import io.qameta.allure.Step;

public class WebDriverTestSteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);

    @Step("Check WebDriver has Been Initialized")
    public void checkWebDriverInitialized() {
        Assert.assertNotNull(homePage.getDriver(), "Driver object is not null");
    }
}

