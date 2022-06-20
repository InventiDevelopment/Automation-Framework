package cz.inventi.qa.framework.tests.framework.web.webobjects;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.WebElementTestSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Framework Unit Tests")
@Story("Test WebElement Object")
public class WebElementTests extends TestBase {
    private WebElementTestSteps webElementTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        webElementTestSteps = new WebElementTestSteps();
    }

    @Test(description = "Basic WebElement Actions Tests")
    public void basicWebElementActionsTest() {
        webElementTestSteps
                .checkParentXpath()
                .scrollToElement();
    }
}