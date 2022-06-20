package cz.inventi.qa.framework.testapps.testweb.webobjects;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import lombok.Getter;

@Getter
@FindElement(xpath = "//footer")
public class Footer<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//div[@id='scrollTop']")
    WebElement<Footer<T>> scrollToTopBtn;
    @FindElement(xpath = "//div", index = 1)
    WebElement<Footer<T>> footerText;

    public Footer(WOProps props) {
        super(props);
    }

    public T scrollToTop () {
        scrollToTopBtn.click();
        return getKlass();
    }
}
