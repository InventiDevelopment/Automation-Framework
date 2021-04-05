package cz.inventi.qa.inventiweb.frontend.core.webobjects;

import cz.inventi.qa.framework.core.annotations.Application;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;

@Application(name = "inventiweb")
public class CareersPage extends BasePage<HomePage> {
    private TopPanel<EventsPage> topPanel;

    public CareersPage(WOProps props) {
        super(props);
    }
}
