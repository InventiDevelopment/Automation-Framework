package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
public class ContactUsPage extends BasePage<ContactUsPage> {
    ContactForm<ContactUsPage> contactForm;

    @FindElement(xpath = "//div[contains(@class, 'addressWrapper')]")
    WebElement<ContactUsPage> addressWrapper;
    @FindElement(xpath = "//div[contains(@class, 'mapWrapper')]")
    WebElement<ContactUsPage> mapWrapper;
    @FindElement(xpath = "//div[contains(@class, 'mapWrapper')]/iframe")
    WebElement<ContactUsPage> map;

    public ContactUsPage(WOProps props) {
        super(props);
    }
}
