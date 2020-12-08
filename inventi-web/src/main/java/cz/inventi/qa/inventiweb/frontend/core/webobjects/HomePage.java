package cz.inventi.qa.inventiweb.frontend.core.webobjects;


import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebElement;
import cz.inventi.qa.framework.testweb.webobjects.BasePage;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopMenu;
import lombok.Getter;
import org.testng.Assert;

@Getter
@Application("inventiweb")
public class HomePage extends BasePage<HomePage> {
    private TopMenu topMenu;

    public HomePage(WOProps props) {
        super(props);
    }
}
