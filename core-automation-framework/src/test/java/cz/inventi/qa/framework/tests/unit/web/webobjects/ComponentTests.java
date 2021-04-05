package cz.inventi.qa.framework.tests.unit.web.webobjects;

import cz.inventi.qa.framework.tests.core.WebTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComponentTests extends WebTestCase {

    @Test
    public void parentXpathAndIndexTest () {
        int index = 1;

        homePage
            .getSidePanel()
            .getSideInfos()
            .get(index);

        Assert.assertEquals(homePage.getSidePanel().getSideInfos().get(index).getXpath(), "(//body//aside//div[contains(@class, 'sideInfo')])[" + (index + 1) + "]");
    }
}
