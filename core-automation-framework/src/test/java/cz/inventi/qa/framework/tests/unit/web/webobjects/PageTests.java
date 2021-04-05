package cz.inventi.qa.framework.tests.unit.web.webobjects;

import cz.inventi.qa.framework.tests.core.WebTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PageTests extends WebTestCase {

    @Test
    public void getPageTitleTest () {
        Assert.assertEquals(homePage.getPageTitle(), "Testing WebPage");
        homePage.assertPageTitle("Testing WebPage");
    }
}
