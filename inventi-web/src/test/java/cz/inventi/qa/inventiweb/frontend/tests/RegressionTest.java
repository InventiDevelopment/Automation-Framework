package cz.inventi.qa.inventiweb.frontend.tests;

import cz.inventi.qa.framework.core.annotations.ConfigFiles;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.test.BaseTest;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.HomePage;
import lombok.Getter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@Getter
@ConfigFiles(driverConfig = "inventiWebDriverConfig.yml", appConfig = "inventiAppsConfig.yml")
public class RegressionTest extends BaseTest {
    protected HomePage homePage;

    @BeforeClass
    @Parameters({"environment", "browser", "language"})
    public void init(@Optional("prod") String environment,
                     @Optional("chrome") String browser,
                     @Optional("CS_CZ") String language) {

        homePage = FrameworkManager.initWebApp(browser, environment, language, HomePage.class);
        homePage.switchLanguageTo(Language.valueOf(language));
    }
}
