package cz.inventi.qa.inventiweb.frontend.core.components.topmenu;

import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import lombok.Getter;

@Getter
public class TopMenu extends WebComponent<TopMenu> {
    private LanguageSwitcher languageSwitcher;

    public TopMenu(WOProps props) {
        super(props);
    }
}
