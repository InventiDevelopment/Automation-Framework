package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.annotations.web.NoParent;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.framework.core.objects.web.WebElement;
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
