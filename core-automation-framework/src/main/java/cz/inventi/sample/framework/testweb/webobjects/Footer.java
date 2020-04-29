package cz.inventi.sample.framework.testweb.webobjects;

import cz.inventi.sample.framework.core.objects.web.WebObject;
import cz.inventi.sample.framework.core.annotations.FindElement;
import cz.inventi.sample.framework.core.objects.web.WOProps;
import cz.inventi.sample.framework.core.objects.web.WebComponent;
import cz.inventi.sample.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
@FindElement(xpath = "//footer")
public class Footer<T extends WebObject> extends WebComponent<T> {

    @FindElement(xpath = "//div[@id='scrollTop']")
    WebElement scrollToTopBtn;
    @FindElement(xpath = "//div", index = 1)
    WebElement footerText;

    public Footer(WOProps props) {
        super(props);
    }

    public T scrollToTop () {
        scrollToTopBtn.click();
        return getKlass();
    }
}
