package cz.inventi.qa.framework.tests.unit.web.webobjects;

import cz.inventi.qa.framework.tests.core.WebTestCase;
import cz.inventi.qa.framework.testweb.webobjects.HomePage;
import cz.inventi.qa.framework.testweb.webobjects.SideInfo;
import cz.inventi.qa.framework.testweb.webobjects.SidePanel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ComponentListTests extends WebTestCase {

    @Test
    public void componentClassTypeTest () {
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

    @Test
    public void getComponentsTest () {
        List<SideInfo<SidePanel<HomePage>>> sideInfoList = homePage
                .getSidePanel()
                .getSideInfos()
                .getComponents();
        Assert.assertEquals(sideInfoList.size(), 2);
    }

    @Test
    public void getTest () {
        SideInfo<SidePanel<HomePage>> sideInfo = homePage
                .getSidePanel()
                .getSideInfos()
                .get(1);
        Assert.assertNotNull(sideInfo);
    }
}
