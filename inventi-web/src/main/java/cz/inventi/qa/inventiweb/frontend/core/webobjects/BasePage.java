package cz.inventi.qa.inventiweb.frontend.core.webobjects;

import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.inventiweb.frontend.core.components.footer.Footer;
import cz.inventi.qa.inventiweb.frontend.core.components.footer.FooterMenu;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;
import lombok.Getter;

@Getter
public class BasePage<T extends WebPage> extends WebPage {
    TopPanel<T> topPanel;
    Footer<T> footer;

    public BasePage(WOProps props) {
        super(props);
    }
}
