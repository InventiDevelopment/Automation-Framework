package cz.inventi.qa.framework.tests.unit.web.webobjects;

import cz.inventi.qa.framework.core.objects.test.WebFlow;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebApplicationObjectWebFlow extends WebFlow {

    @Test
    public void returnTypeTest () {
        HomePage returnedHomePage = homePage
            .getMenu()
            .leaveComponent();

        Assert.assertEquals(returnedHomePage.getClass(), HomePage.class);
    }

}
