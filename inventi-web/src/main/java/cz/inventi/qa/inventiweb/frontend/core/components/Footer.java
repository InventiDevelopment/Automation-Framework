package cz.inventi.qa.inventiweb.frontend.core.components;

import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebObject;
import cz.inventi.qa.inventiweb.frontend.core.components.topmenu.LanguageSwitcher;

public class Footer<T extends WebObject> extends WebComponent<T> {
    LanguageSwitcher languageSwitcher;

    public Footer(WOProps props) {
        super(props);
    }
}
