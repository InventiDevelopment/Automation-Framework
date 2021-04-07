package cz.inventi.qa.framework.tests.core;

import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.objects.test.BaseTest;
import cz.inventi.qa.framework.testweb.webobjects.HomePage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@ConfigFiles(driverConfig = "config/customWebDriverConfig.yml", appsConfig = "config/customAppsConfig.yml")
public class CustomConfigTestCase extends BaseTest {
    public HomePage homePage;

    @BeforeClass
    @Parameters({"environment", "language", "browser"})
    public void init(@Optional("PROD") String environment,
                     @Optional("EN") String language,
                     @Optional("chrome") String browser) {

        homePage = initWebAppInstance(browser, environment, language, HomePage.class);
    }

    @AfterClass
    public void quit() {
        if (!getConfigManager().getRunMode().equals(RunMode.DEBUG)) {
            getWebDriverManager().cleanDriver();
        }
    }
}
