package cz.inventi.qa.framework.tests.unit.webobjects;

import cz.inventi.qa.framework.core.factories.webobject.WebObjectFactory;
import cz.inventi.qa.framework.tests.core.TestCase;
import cz.inventi.qa.framework.testweb.webobjects.WhatWeDoPage;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WebElementTests extends TestCase {

    @Test
    public void parentXpathTest () {
        WebElement tooltipImg = homePage.getTooltipImg();

        Assert.assertEquals(tooltipImg.getXpath(), "//body//div[contains(@class, 'toolTipImage')]");
    }

    @Test
    public void clickJSTest () {
        WhatWeDoPage whatWeDoPage = WebObjectFactory.initPage(WhatWeDoPage.class);
        homePage
            .getMenu()
            .clickWhatWeDo()
            .getAppendContentBtn()
            .clickJS();

        Assert.assertTrue(whatWeDoPage.getAppendedContent().isDisplayed());
    }

    @Test
    public void hoverTest () {
        WhatWeDoPage whatWeDoPage = WebObjectFactory.initPage(WhatWeDoPage.class);
        whatWeDoPage
            .getMenu()
            .clickHome()
            .getTooltipImg()
            .hover();

        Assert.assertTrue(homePage.getToolTipText().isDisplayed());
    }

    @Test
    public void scrollToTest () {
        homePage
            .getFooter()
            .getScrollToTopBtn()
            .scrollTo();

        Assert.assertTrue(homePage.getFooter().getScrollToTopBtn().isDisplayed());
    }
}
