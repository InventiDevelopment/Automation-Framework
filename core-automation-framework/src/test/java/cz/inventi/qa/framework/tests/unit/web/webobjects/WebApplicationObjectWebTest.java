package cz.inventi.qa.framework.tests.unit.web.webobjects;

import cz.inventi.qa.framework.tests.core.WebTestCase;
import cz.inventi.qa.framework.testweb.webobjects.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebApplicationObjectWebTest extends WebTestCase {

    @Test
    public void returnTypeTest () {
        HomePage returnedHomePage = homePage
            .getMenu()
            .leaveComponent();

        Assert.assertEquals(returnedHomePage.getClass(), HomePage.class);
    }

}
