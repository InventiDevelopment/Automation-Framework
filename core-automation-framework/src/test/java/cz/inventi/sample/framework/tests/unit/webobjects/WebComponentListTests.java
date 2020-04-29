package cz.inventi.sample.framework.tests.unit.webobjects;

import cz.inventi.sample.framework.testweb.webobjects.HomePage;
import cz.inventi.sample.framework.tests.core.TestCase;
import cz.inventi.sample.framework.testweb.webobjects.SideInfo;
import cz.inventi.sample.framework.testweb.webobjects.SidePanel;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class WebComponentListTests extends TestCase {

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
