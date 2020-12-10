package cz.inventi.qa.inventiweb.frontend.core.webobjects;

import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.TopPanel;

public class EventsPage extends WebPage {
    private TopPanel<EventsPage> topPanel;

    public EventsPage(WOProps props) {
        super(props);
    }
}
