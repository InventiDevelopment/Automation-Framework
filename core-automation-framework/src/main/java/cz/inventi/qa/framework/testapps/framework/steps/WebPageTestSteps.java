package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.objects.test.StepsBase;
import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import io.qameta.allure.Step;

public class WebPageTestSteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);

    @Step("Check leaveComponent() Method Returns Correct Object")
    public WebPageTestSteps checkLeaveComponentReturnValue() {
        HomePage returnedHomePage = homePage
                .getMenu()
                .leaveComponent();

        Assert.assertEquals(returnedHomePage.getClass(), HomePage.class,
                "Returned object for leaving Menu is HomePage.class");
        return this;
    }

    @Step("Check WebPage Has Title '{webPageTitle}'")
    public WebPageTestSteps checkWebPageTitleIs(String webPageTitle) {
        Assert.assertEquals(homePage.getPageTitle(), webPageTitle, "Title is correct");
        return this;
    }
}

