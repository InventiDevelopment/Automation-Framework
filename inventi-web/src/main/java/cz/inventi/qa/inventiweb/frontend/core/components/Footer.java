package cz.inventi.qa.inventiweb.frontend.core.components;

import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.LanguageSwitcher;

public class Footer extends WebComponent<Footer> {
    private LanguageSwitcher languageSwitcher;

    public Footer(WOProps props) {
        super(props);
    }
}
