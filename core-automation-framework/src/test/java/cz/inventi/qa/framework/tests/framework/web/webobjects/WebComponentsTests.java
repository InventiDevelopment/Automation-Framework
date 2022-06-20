package cz.inventi.qa.framework.tests.framework.web.webobjects;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.WebComponentsTestSteps;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SideInfo;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Framework Unit Tests")
@Story("Test WebComponent Object")
public class WebComponentsTests extends TestBase {
    private WebComponentsTestSteps webComponentsTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        webComponentsTestSteps = new WebComponentsTestSteps();
    }

    @Test(description = "Test Working with ComponentLists")
    public void checkComponentXpath() {
        webComponentsTestSteps.checkWebComponentXpathIsCorrect();
    }

    @Test(description = "Test Working with ComponentLists", dependsOnMethods = "checkComponentXpath")
    public void componentListTest() {
        webComponentsTestSteps
                .checkSideInfoItemsType(SideInfo.class)
                .checkSideInfoItemsCount(2)
                .tryToRetrieveSideInfoItem(1);
    }
}
