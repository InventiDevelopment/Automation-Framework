package cz.inventi.qa.framework.tests.unit.web.webobjects;

import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.tests.core.WebTestCase;
import cz.inventi.qa.framework.testweb.webobjects.WhatWeDoPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ElementTests extends WebTestCase {
    private WhatWeDoPage whatWeDoPage;

    @Test
    public void parentXpathTest () {
        WebElement tooltipImg = homePage.getTooltipImg();

        Assert.assertEquals(tooltipImg.getXpath(), "//body//div[contains(@class, 'toolTipImage')]");
    }

    @Test
    public void clickJSTest () {
        whatWeDoPage = homePage
            .getMenu()
            .clickWhatWeDo();
        whatWeDoPage
            .getAppendContentBtn()
            .clickJS();

        Assert.assertTrue(whatWeDoPage.getAppendedContent().isDisplayed());
    }

    @Test
    public void hoverTest () {
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
