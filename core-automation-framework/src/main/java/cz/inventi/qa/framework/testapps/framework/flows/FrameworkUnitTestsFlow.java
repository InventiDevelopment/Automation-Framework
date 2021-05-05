package cz.inventi.qa.framework.testapps.framework.flows;

import cz.inventi.qa.framework.core.objects.test.FlowBase;
import cz.inventi.qa.framework.core.objects.web.WebComponentList;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SideInfo;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SidePanel;
import io.qameta.allure.Step;
import org.testng.Assert;

public class FrameworkUnitTestsFlow extends FlowBase {
    public static final HomePage homePage = getWebAppInstance(HomePage.class);

    @Step("Load Test Web Side Info Items as Component List")
    public static void checkSideInfoItemsType(Class<?> classType) {
        Assert.assertEquals(getSideInfos().getComponents().get(0).getClass(), classType);
        Assert.assertEquals(getSideInfos().get(0).getClass(), classType);
    }

    @Step("Check That There Is ({'count'}) Info Items")
    public static void checkSideInfoItemsCount(int count) {
        Assert.assertEquals(getSideInfos().getComponents().size(), count);
    }

    @Step("Get Particular Info Item ({'item'})")
    public static void tryToRetrieveSideInfoItem(int item) {
        Assert.assertNotNull(getSideInfos().get(1));
    }

    private static WebComponentList<SideInfo<SidePanel<HomePage>>> getSideInfos() {
        return homePage
                .getSidePanel()
                .getSideInfos();
    }
}

