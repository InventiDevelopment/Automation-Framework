package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.objects.test.steps.StepsBase;
import cz.inventi.qa.framework.core.objects.web.WebComponentList;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SideInfo;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SidePanel;
import io.qameta.allure.Step;
import org.testng.Assert;

public class WebComponentsTestSteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);

    @Step("Load Test Web Side Info Items as Component List")
    public WebComponentsTestSteps checkSideInfoItemsType(Class<?> classType) {
        Assert.assertEquals(getSideInfos().getComponents().get(0).getClass(), classType);
        Assert.assertEquals(getSideInfos().get(0).getClass(), classType);
        return this;
    }

    @Step("Check That There Is ({count}) Info Items")
    public WebComponentsTestSteps checkSideInfoItemsCount(int count) {
        Assert.assertEquals(getSideInfos().getComponents().size(), count);
        return this;
    }

    @Step("Get Particular Info Item ({item})")
    public WebComponentsTestSteps tryToRetrieveSideInfoItem(int item) {
        Assert.assertNotNull(getSideInfos().get(item));
        return this;
    }

    @Step("Check Component Xpath For The TestWeb")
    public WebComponentsTestSteps checkWebComponentXpathIsCorrect() {
        int index = 1;
        homePage
                .getSidePanel()
                .getSideInfos()
                .get(index);

        Assert.assertEquals(homePage.getSidePanel().getSideInfos().get(index).getXpath(),
                "(//body//aside//div[contains(@class, 'sideInfo')])[" + (index + 1) + "]");
        return this;
    }

    private WebComponentList<SideInfo<SidePanel<HomePage>>> getSideInfos() {
        return homePage
                .getSidePanel()
                .getSideInfos();
    }
}

