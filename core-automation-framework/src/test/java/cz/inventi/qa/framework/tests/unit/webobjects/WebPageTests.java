package cz.inventi.qa.framework.tests.unit.webobjects;

import cz.inventi.qa.framework.tests.core.TestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebPageTests extends TestCase {

    @Test
    public void getPageTitleTest () {
        Assert.assertEquals(homePage.getPageTitle(), "Testing WebPage");
        homePage.assertPageTitle("Testing WebPage");
    }
}
