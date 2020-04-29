package cz.inventi.sample.framework.tests.unit.webobjects;

import cz.inventi.sample.framework.tests.core.TestCase;
import cz.inventi.sample.framework.testweb.webobjects.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebObjectTest extends TestCase {

    @Test
    public void returnTypeTest () {
        HomePage returnedHomePage = homePage
            .getMenu()
            .leaveComponent();

        Assert.assertEquals(returnedHomePage.getClass(), HomePage.class);
    }

}
