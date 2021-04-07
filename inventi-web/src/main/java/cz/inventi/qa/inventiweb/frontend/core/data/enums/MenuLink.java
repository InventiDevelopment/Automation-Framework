package cz.inventi.qa.inventiweb.frontend.core.data.enums;

import cz.inventi.qa.framework.core.objects.framework.AppInstance;

public enum MenuLink {
    WHO_WE_ARE,
    WHAT_WE_DO,
    CASE_STUDIES,
    EVENTS,
    CAREERS,
    CONTACTS;

    public String getText(AppInstance appInstance) {
        return appInstance.getLanguageManager().getTranslation(this.toString());
    }
}
