package cz.inventi.qa.framework.core.objects.parameters;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.data.enums.Language;

public class CommonParameters {
    private final String environment;
    private Language language;

    public CommonParameters(String language, String environment) {
        this.environment = environment;
        setLanguage(language);
    }

    public void setLanguage(String language) {
        try {
            setLanguage(Language.valueOf(language.toUpperCase()));
        } catch (IllegalArgumentException e) {
            Log.fail("The '" + language + "' language is not supported.");
        }
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getEnvironment() {
        return environment;
    }

    public Language getLanguage() {
        return language;
    }
}
