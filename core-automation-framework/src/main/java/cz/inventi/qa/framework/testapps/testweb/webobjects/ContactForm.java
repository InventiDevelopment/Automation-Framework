package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import lombok.Getter;

@Getter
@FindElement(xpath = "//form[@id='contact-form']")
public class ContactForm<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//input[@name='email']")
    WebElement<ContactForm<T>> contactFormEmailInput;
    @FindElement(xpath = "//input[@name='name']")
    WebElement<ContactForm<T>> contactFormNameInput;
    @FindElement(xpath = "//textarea[@name='message']")
    WebElement<ContactForm<T>> contactFormMessageTextArea;
    @FindElement(xpath = "//form//textarea[@name='message']")
    WebElement<ContactForm<T>> submitContactFormBtn;

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
