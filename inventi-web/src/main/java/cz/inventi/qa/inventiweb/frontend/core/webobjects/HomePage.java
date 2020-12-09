package cz.inventi.qa.inventiweb.frontend.core.webobjects;


import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.annotations.FindElement;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.inventiweb.frontend.core.components.footer.FooterMenu;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;
import lombok.Getter;

@Getter
@Application("inventiweb")
@FindElement(xpath = "//body")
public class HomePage extends BasePage<HomePage> {
    private FooterMenu<HomePage> footerMenu;
    private TopPanel<HomePage> topPanel;

    public HomePage(WOProps props) {
        super(props);
    }

    public HomePage homePageIsDisplayed() {
        topPanel.logoImg();
        footerMenu.footerMenuIsDisplayed();
        return this;
    }
}
