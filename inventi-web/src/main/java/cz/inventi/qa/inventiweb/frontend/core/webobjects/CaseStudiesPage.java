package cz.inventi.qa.inventiweb.frontend.core.webobjects;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;
import org.junit.Assert;

@Application("inventiweb")
public class CaseStudiesPage extends BasePage<HomePage> {
    private TopPanel<EventsPage> topPanel;

    public static final String expectedTitle = "We invent great ideas";


    @FindElement(xpath = "//*[@id='main']//h1")
    private WebElement caseStudiesTitle;

    public CaseStudiesPage(WOProps props) {
        super(props);
    }

    public CaseStudiesPage assertCaseStudiesPageTitle() {
        String titleString = caseStudiesTitle.getText();
        Assert.assertTrue(titleString.contains(expectedTitle));
        return this;
    }

}
