package cz.inventi.sample.framework.tests.unit.webobjects;

import cz.inventi.sample.framework.tests.core.TestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebComponentTests extends TestCase {

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
