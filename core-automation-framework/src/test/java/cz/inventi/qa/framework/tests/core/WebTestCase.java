package cz.inventi.qa.framework.tests.core;

import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.test.BaseTest;
import cz.inventi.qa.framework.testweb.webobjects.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class WebTestCase extends BaseTest {
    private AppInstance appInstance;
    public HomePage homePage;

    @BeforeClass
    @Parameters({"environment", "language", "browser"})
    public void init(@Optional("PROD") String environment,
                     @Optional("EN") String language,
                     @Optional("chrome") String browser) {

        homePage = initWebAppInstance(browser, environment, language, HomePage.class);
        appInstance = homePage.getAppInstance();
    }

    @AfterClass
    public void quit() {
        if (!appInstance.getConfigManager().getRunMode().equals(RunMode.DEBUG)) {
            appInstance.getWebDriverManager().cleanDriver();
        }
    }
}
