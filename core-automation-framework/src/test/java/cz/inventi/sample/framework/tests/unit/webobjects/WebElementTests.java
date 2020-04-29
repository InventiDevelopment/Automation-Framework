package cz.inventi.sample.framework.tests.unit.webobjects;

import cz.inventi.sample.framework.testweb.webobjects.WhatWeDoPage;
import cz.inventi.sample.framework.core.factories.webobject.WebObjectFactory;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import cz.inventi.sample.framework.tests.core.TestCase;
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
