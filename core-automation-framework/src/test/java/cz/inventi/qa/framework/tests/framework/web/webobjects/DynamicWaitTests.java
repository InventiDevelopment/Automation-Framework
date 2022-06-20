package cz.inventi.qa.framework.tests.framework.web.webobjects;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.framework.steps.WebElementTestSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Framework Unit Tests")
@Story("Test WebElement Object")
public class DynamicWaitTests extends TestBase {
    private WebElementTestSteps webElementTestSteps;

    @BeforeClass
    @Override
    public void initSteps() {
        webElementTestSteps = new WebElementTestSteps();
    }

    @Test(description = "Dynamic Wait Related WebElement Basic Actions Tests")
    public void waitRelatedBasicWebElementActionsTests() {
        webElementTestSteps
                .clickJs()
                .hoverOverElement()
                .clickAppendedButton();
    }

    @Test(
            description = "Check Exception Is Returned If Default Wait Is Turned Off",
            expectedExceptions = NoSuchElementException.class
    )
    public void doNotWaitAutomaticallyExceptionTest() {
        webElementTestSteps.clickAppendedButton();
    }
}