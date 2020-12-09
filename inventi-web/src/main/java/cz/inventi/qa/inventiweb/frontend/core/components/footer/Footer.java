package cz.inventi.qa.inventiweb.frontend.core.components.footer;

import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.inventiweb.frontend.core.webobjects.HomePage;

public class Footer <T extends WebObject> extends WebComponent {
    private FooterMenu<HomePage> footerMenu;

    public Footer(WOProps props) {
        super(props);
    }

    public Footer footerMenuIsDisplay(){
        footerMenu.footerMenuIsDisplay();
        return this;
    }

}
