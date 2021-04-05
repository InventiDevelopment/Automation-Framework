package cz.inventi.qa.inventiweb.frontend.core.webobjects;


import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;
import org.junit.Assert;

@Application(name = "inventiweb")
public class ContactsPage extends BasePage<HomePage> {
    private TopPanel<EventsPage> topPanel;

    public static final String expectedTitle = "Let's get acquainted";

    @FindElement(xpath = "//*[@id='main']//h1")
    private WebElement contactTitle;

    public ContactsPage(WOProps props) {
        super(props);
    }

    public ContactsPage assertContactsPageTitle() {
        String titleString = contactTitle.getText();
        Assert.assertTrue(titleString.contains(expectedTitle));
        return this;
    }
}
