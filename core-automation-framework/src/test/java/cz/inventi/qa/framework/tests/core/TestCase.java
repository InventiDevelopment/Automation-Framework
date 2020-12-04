package cz.inventi.qa.framework.tests.core;

import cz.inventi.qa.framework.core.objects.test.BaseTest;
import cz.inventi.qa.framework.core.managers.DriverManager;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.testweb.webobjects.HomePage;
import org.testng.annotations.*;

public class TestCase extends BaseTest {
    public HomePage homePage;

    @BeforeClass
    @Parameters({"environment", "language", "browser"})
    public void init(@Optional("PROD") String environment,
                     @Optional("EN") String language,
                     @Optional("chrome") String browser) {

        homePage = FrameworkManager.initWebApp(browser, environment, language, HomePage.class);
    }

    @AfterClass
    public void quit() {
        DriverManager.cleanDriver();
    }
}
