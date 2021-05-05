package cz.inventi.qa.framework.testapps.testweb.flows;

import cz.inventi.qa.framework.core.objects.test.FlowBase;
import cz.inventi.qa.framework.testapps.testapi.dtos.PostResponseDto;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SideInfo;
import cz.inventi.qa.framework.testapps.testweb.webobjects.SidePanel;
import io.qameta.allure.Step;
import org.testng.Assert;

import java.util.List;

public class TestWebBasicFlow extends FlowBase {
    protected static HomePage homePage = getWebAppInstance(HomePage.class);

    @Step("Load Side Info Items")
    public static List<SideInfo<SidePanel<HomePage>>> getSideInfoItems() {
        List<SideInfo<SidePanel<HomePage>>> sideInfoList = homePage
                .getSidePanel()
                .getSideInfos()
                .getComponents();
        Assert.assertEquals(sideInfoList.get(0).getClass(), SideInfo.class);

        SideInfo<SidePanel<HomePage>> sideInfo = homePage
                .getSidePanel()
                .getSideInfos()
                .get(0);
        Assert.assertEquals(sideInfo.getClass(), SideInfo.class);
        return sideInfoList;
    }


    estWebBasicFlow.getSideInfos();
    TestWebBasicFlow.
            List<SideInfo<SidePanel<HomePage>>> sideInfoList = homePage
            .getSidePanel()
            .getSideInfos()
            .getComponents();
        Assert.assertEquals(sideInfoList.get(0).getClass(), SideInfo.class);

    SideInfo<SidePanel<HomePage>> sideInfo = homePage
            .getSidePanel()
            .getSideInfos()
            .get(0);
        Assert.assertEquals(sideInfo.getClass(), SideInfo.class);
}

