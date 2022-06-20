package cz.inventi.qa.sampleapplication.tests;

import cz.inventi.qa.framework.core.objects.test.TestBase;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Provide Sample Structure for Custom Module")
@Story("Provide Sample Test for Custom Module")
public class SampleTest extends TestBase {

    @BeforeClass
    @Override
    public void initSteps() {
    }

    @Test(description = "This Is a Sample Test")
    public void sampleTest() {
        // Write some test here
    }
}
