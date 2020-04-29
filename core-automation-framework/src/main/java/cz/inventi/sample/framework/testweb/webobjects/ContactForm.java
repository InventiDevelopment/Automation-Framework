package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.objects.web.WebObject;
import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebComponent;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
@FindElement(xpath = "//form[@id='contact-form']")
public class ContactForm<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//input[@name='email']")
    WebElement contactFormEmailInput;
    @FindElement(xpath = "//input[@name='name']")
    WebElement contactFormNameInput;
    @FindElement(xpath = "//textarea[@name='message']")
    WebElement contactFormMessageTextArea;
    @FindElement(xpath = "//form//textarea[@name='message']")
    WebElement submitContactFormBtn;

    public ContactForm(WOProps props) {
        super(props);
    }

    public ContactForm<T> sendContactForm (String name, String email, String message) {
        contactFormEmailInput.sendKeys(email);
        contactFormNameInput.sendKeys(name);
        contactFormMessageTextArea.sendKeys(message);
        submitContactFormBtn.click();
        return this;
    }
}
