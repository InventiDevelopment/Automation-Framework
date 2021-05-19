package cz.inventi.qa.framework.tests.testweb;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import cz.inventi.qa.framework.testapps.testweb.steps.PageIntegritySteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Provide Sample Web Application Tests")
@Story("Test TestWeb Application")
public class SampleTestWebTests extends TestBase {
    private PageIntegritySteps pageIntegritySteps;

    @BeforeClass
    @Override
    public void initSteps() {
        pageIntegritySteps = new PageIntegritySteps();
    }

    @Test(description = "Check Page Integrity")
    public void checkPageIntegrity() {
        pageIntegritySteps
            .checkMenuIsVisible()
            .checkFooterIsVisible()
            .checkSidePanelIsVisible()
            .checkMainContentIsVisible();
    }
}
