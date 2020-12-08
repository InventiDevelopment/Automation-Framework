package cz.inventi.qa.inventiweb.frontend.core.webobjects;


import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import lombok.Getter;

@Getter
@Application("inventiweb")
@FindElement(xpath = "//body")
public class HomePage extends BasePage {

    @FindElement(xpath = "//h1[@class='center']")
    WebElement sth;

    public HomePage(WOProps props) {
        super(props);
    }
}
