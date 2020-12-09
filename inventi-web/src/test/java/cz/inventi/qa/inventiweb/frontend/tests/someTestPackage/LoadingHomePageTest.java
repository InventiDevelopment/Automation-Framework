package cz.inventi.qa.inventiweb.frontend.tests.someTestPackage;

import cz.inventi.qa.inventiweb.frontend.tests.RegressionTest;
import org.testng.annotations.Test;

public class LoadingHomePageTest extends RegressionTest {

    @Test
    public void homePageIsLoafing() {
        homePage.homePageIsDisplayed();
    }
}
