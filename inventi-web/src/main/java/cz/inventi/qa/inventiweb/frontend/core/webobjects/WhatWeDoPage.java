package cz.inventi.qa.inventiweb.frontend.core.webobjects;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;
import org.testng.Assert;

@Application(name = "inventiweb")
public class WhatWeDoPage extends BasePage<HomePage> {
    private TopPanel<EventsPage> topPanel;

    public static final String expectedTitle = "We create strong partnerships";


    @FindElement(xpath = "//*[@id='main']//h1")
    private WebElement whatWeDoTitle;

    public WhatWeDoPage(WOProps props) {
        super(props);
    }

    public WhatWeDoPage assertWhatWeDoPageTitle() {
        String titleString = whatWeDoTitle.getText();
        Assert.assertTrue(titleString.contains(expectedTitle));
        return this;
    }
}
