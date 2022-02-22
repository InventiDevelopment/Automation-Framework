package cz.inventi.qa.framework.testapps.framework.steps;

import cz.inventi.qa.framework.core.objects.test.assertions.Assert;
import cz.inventi.qa.framework.core.objects.test.steps.StepsBase;
import cz.inventi.qa.framework.testapps.testweb.webobjects.HomePage;
import cz.inventi.qa.framework.testapps.testweb.webobjects.WhatWeDoPage;
import io.qameta.allure.Step;

public class WebElementTestSteps extends StepsBase {
    private final HomePage homePage = getWebAppInstanceOf(HomePage.class);
    private WhatWeDoPage whatWeDoPage;

    @Step("Try to Click Appended Button of TestWeb")
    public WebElementTestSteps clickAppendedButton() {
        homePage
                .getMenu()
                .clickWhatWeDo()
                .clickAppendContent()
                .clickRefreshAppendedContent();
        return this;
    }

    @Step("Check Parent XPath Locator Is Correct for TestWeb WebElement")
    public WebElementTestSteps checkParentXpath() {
        Assert.assertEquals(homePage.getTooltipImg().getXpath(),
                "//body//div[contains(@class, 'toolTipImage')]",
                "Parent Xpath is correct");
        return this;
    }

    @Step("Try to Perform JS Click on an WebElement of TestWeb")
    public WebElementTestSteps clickJs() {
        whatWeDoPage = homePage
                .getMenu()
                .clickWhatWeDo();
        whatWeDoPage
                .getAppendContentBtn()
                .clickJS();
        Assert.assertTrue(
                whatWeDoPage.getAppendedContent().isDisplayed(),
                "Appended content is displayed"
        );
        return this;
    }

    @Step("Try to Hover Over WebElement of TestWeb application")
    public WebElementTestSteps hoverOverElement() {
        whatWeDoPage
                .getMenu()
                .clickHome()
                .getTooltipImg()
                .hover();
        Assert.assertTrue(
                homePage.getToolTipText().isDisplayed(),
                "Tooltip text is now visible on hover"
        );
        return this;
    }

    @Step("Try to Scroll to an Element of TestWeb's Footer")
    public WebElementTestSteps scrollToElement() {
        homePage
                .getFooter()
                .getScrollToTopBtn()
                .scrollTo();
        Assert.assertTrue(
                homePage.getFooter().getScrollToTopBtn().isDisplayed(),
                "Footer element is displayed"
        );
        return this;
    }
}

