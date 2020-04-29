package cz.inventi.sample.framework.tests.core;

import cz.inventi.sample.framework.core.annotations.ConfigFiles;
import cz.inventi.sample.framework.core.managers.DriverManager;
import cz.inventi.sample.framework.core.managers.FrameworkManager;
import cz.inventi.sample.framework.core.objects.test.BaseTest;
import cz.inventi.sample.framework.testweb.webobjects.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@ConfigFiles(driverConfig = "config/customDriverConfig.yml", appConfig = "config/customAppConfig.yml")
public class CustomConfigTestCase extends BaseTest {
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
