package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
public class WhatWeDoPage extends BasePage<WhatWeDoPage> {

    @FindElement(xpath = "//table[@id='careerOptions']")
    WebElement<WhatWeDoPage> careerOptionsTable;
    @FindElement(xpath = "//button[@id='appendSomeContent']")
    WebElement<WhatWeDoPage> appendContentBtn;
    @FindElement(xpath = "//button[@id='refreshSomeContent']")
    WebElement<WhatWeDoPage> refreshContentBtn;
    @FindElement(xpath = "//div[@id='appendedContent']")
    WebElement<WhatWeDoPage> appendedContent;

    public WhatWeDoPage(WOProps props) {
        super(props);
    }

    public WhatWeDoPage clickAppendContent() {
        appendContentBtn.click();
        appendedContent.waitUntilIsDisplayed();
        return this;
    }

    public WhatWeDoPage clickRefreshAppendedContent() {
        refreshContentBtn.click();
        return this;
    }
}
