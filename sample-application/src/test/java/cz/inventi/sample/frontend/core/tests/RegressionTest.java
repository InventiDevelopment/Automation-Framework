package cz.inventi.sample.frontend.core.tests;

import cz.inventi.sample.framework.core.annotations.ConfigFiles;
import cz.inventi.sample.framework.core.managers.DriverManager;
import cz.inventi.sample.framework.core.managers.FrameworkManager;
import cz.inventi.sample.framework.core.objects.test.BaseTest;
import cz.inventi.sample.framework.testweb.webobjects.HomePage;
import lombok.Getter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Getter
@ConfigFiles(appConfig = "appConfig.yml")
public class RegressionTest extends BaseTest {
    protected HomePage homePage;

    @BeforeClass
    @Parameters({"environment", "browser"})
    public void init(@Optional("dev") String environment,
                     @Optional("chrome") String browser) {

        FrameworkManager.initWebApp(browser, environment, HomePage.class);
    }

    public HomePage getHomePage() {
        return homePage;
    }

    @AfterClass
    public void quit() {
        DriverManager.cleanDriver();
    }
}
