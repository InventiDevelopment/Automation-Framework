package cz.inventi.qa.inventiweb.frontend.core.components.footer;

import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebObject;

public class Footer <T extends WebObject> extends WebComponent<T> {
    private FooterMenu<T> footerMenu;

    public Footer(WOProps props) {
        super(props);
    }

    public Footer<T> footerMenuIsDisplay(){
        footerMenu.footerMenuIsDisplay();
        return this;
    }

}
