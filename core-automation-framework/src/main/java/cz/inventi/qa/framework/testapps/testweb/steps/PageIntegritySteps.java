package cz.inventi.qa.framework.testapps.testweb.steps;

import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.core.objects.test.StepsBase;
import cz.inventi.qa.framework.core.objects.web.WebComponentList;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SideInfo;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SidePanel;
import io.qameta.allure.Step;

import java.util.List;

public class PageIntegritySteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);

    @Step("Check Menu Is Present")
    public PageIntegritySteps checkMenuIsVisible() {
        List<WebElement> menuLinks = homePage.getMenu().getMenuLinks();
        Assert.assertTrue(menuLinks.get(0).isDisplayed(), "Check menu object is not null");
        Assert.assertEquals(menuLinks.size(), 3,"Check there are three menu items");
        return this;
    }

    @Step("Check Side Panel Is Present")
    public PageIntegritySteps checkSidePanelIsVisible() {
        WebComponentList<SideInfo<SidePanel<HomePage>>> sideInfoList = homePage.getSidePanel().getSideInfos();
        Assert.assertNotEquals(sideInfoList.getComponents().size(), 0,"Check there is at least one side info item");
        Assert.assertTrue(sideInfoList.get(0).getInfoTitle().isDisplayed(),"Check side panel title is visible");
        return this;
    }

    @Step("Check Footer Is Present")
    public PageIntegritySteps checkFooterIsVisible() {
        Assert.assertNotNull(homePage.getFooter().getFooterText().isDisplayed(),"Check footer text is displayed");
        return this;
    }

    @Step("Check Main Content Is Visible")
    public PageIntegritySteps checkMainContentIsVisible() {
        Assert.assertTrue(homePage.getMainContentDiv().isDisplayed(),"Check footer text is displayed");
        return this;
    }
}

