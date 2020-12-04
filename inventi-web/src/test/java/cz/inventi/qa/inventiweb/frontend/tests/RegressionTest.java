package cz.inventi.qa.inventiweb.frontend.tests;

import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.managers.DriverManager;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.test.BaseTest;

import cz.inventi.qa.inventiweb.frontend.core.webobjects.HomePage;
import lombok.Getter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Getter
// TODO fix when no configuration files supplied
// TODO fix when no language parameter set
@ConfigFiles(driverConfig = "inventiDriverConfig.yml", appConfig = "inventiAppConfig.yml")
public class RegressionTest extends BaseTest {
    protected HomePage homePage;

    @BeforeClass
    @Parameters({"environment", "browser"})
    public void init(@Optional("prod") String environment,
                     @Optional("chrome") String browser) {

        homePage = FrameworkManager.initWebApp(browser, environment, HomePage.class);
    }

    public HomePage getHomePage() {
        return homePage;
    }

    @AfterClass
    public void quit() {
        DriverManager.cleanDriver();
    }
}
