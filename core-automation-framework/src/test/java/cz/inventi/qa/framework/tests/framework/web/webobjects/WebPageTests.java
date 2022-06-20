package cz.inventi.qa.framework.tests.framework.web.webobjects;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.WebPageTestSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Framework Unit Tests")
@Story("Test WebPage Object")
public class WebPageTests extends TestBase {
    private WebPageTestSteps webPageTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        webPageTestSteps = new WebPageTestSteps();
    }

    @Test(description = "Check That Correct Class Is Returned for leaveComponent() method")
    public void pageFactoryReturnTypeTest() {
        webPageTestSteps.checkLeaveComponentReturnValue();
    }

    @Test(description = "Check WebPage Title")
    public void getPageTitleTest() {
        webPageTestSteps.checkWebPageTitleIs("Testing WebPage");
    }
}
