package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
public class ContactUsPage extends BasePage<ContactUsPage> {
    ContactForm<ContactUsPage> contactForm;

    @FindElement(xpath = "//div[contains(@class, 'addressWrapper')]")
    WebElement addressWrapper;
    @FindElement(xpath = "//div[contains(@class, 'mapWrapper')]")
    WebElement mapWrapper;
    @FindElement(xpath = "//div[contains(@class, 'mapWrapper')]/iframe")
    WebElement map;

    public ContactUsPage(WOProps props) {
        super(props);
    }

}
