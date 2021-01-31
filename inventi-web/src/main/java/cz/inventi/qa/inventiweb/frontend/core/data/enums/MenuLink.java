package cz.inventi.qa.inventiweb.frontend.core.data.enums;

import cz.inventi.qa.framework.core.managers.LanguageManager;

public enum  MenuLink {
    WHO_WE_ARE,
    WHAT_WE_DO,
    CASE_STUDIES,
    EVENTS,
    CAREERS,
    CONTACTS;

    public String getText() {
        return LanguageManager.getTranslation(this.toString());
    }
}
