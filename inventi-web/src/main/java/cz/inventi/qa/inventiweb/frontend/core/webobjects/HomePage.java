package cz.inventi.qa.inventiweb.frontend.core.webobjects;


import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import lombok.Getter;

@Getter
@Application("inventiweb")
@FindElement(xpath = "//body")
public class HomePage extends BasePage<HomePage> {

    public HomePage(WOProps props) {
        super(props);
    }
}
