package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.annotations.NoParent;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebComponent;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import cz.inventi.sample.framework.core.objects.web.WebObject;
import lombok.Getter;

@Getter
@NoParent
@FindElement(xpath = "//div[@id='popup']")
public class Popup<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "/p")
    WebElement popupText;
    @FindElement(xpath = "/button")
    WebElement closePopupBtn;

    public Popup(WOProps props) {
        super(props);
    }

    public T closePopup () {
        closePopupBtn.click();
        return getKlass();
    }
}
