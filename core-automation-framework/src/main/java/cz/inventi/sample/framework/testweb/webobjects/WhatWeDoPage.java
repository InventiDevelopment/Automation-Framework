package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
public class WhatWeDoPage extends BasePage<WhatWeDoPage> {

    @FindElement(xpath = "//table[@id='careerOptions']")
    WebElement careerOptionsTable;
    @FindElement(xpath = "//button[@id='appendSomeContent']")
    WebElement appendContentBtn;
    @FindElement(xpath = "//button[@id='refreshSomeContent']")
    WebElement refreshContentBtn;
    @FindElement(xpath = "//div[@id='appendedContent']")
    WebElement appendedContent;

    public WhatWeDoPage(WOProps props) {
        super(props);
    }

    public WhatWeDoPage clickAppendContent () {
        appendContentBtn.click();
        appendedContent.waitUntilDisplayed();
        return this;
    }

    public WhatWeDoPage clickAppendContentWithoutWait () {
        appendContentBtn.click();
        return this;
    }

    public WhatWeDoPage clickRefreshAppendedContent () {
        refreshContentBtn.click();
        return this;
    }
}
