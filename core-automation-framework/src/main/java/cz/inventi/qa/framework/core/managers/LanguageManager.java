package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.data.config.LanguageData;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class LanguageManager {
    private static final String LANGUAGE_FOLDER = "lang";
    private final AppInstance<?> appInstance;
    private Language currentLanguage;
    private LanguageData languageData;
    private Map<String, String> dictionary;

    public LanguageManager(AppInstance<?> appInstance) {
        this.appInstance = appInstance;
        init(TestSuiteParameters.getParameter("language"));
    }

    public void init(Language language) {
        URL languageFile = loadLanguageFile();
        currentLanguage = language;
        if (!Language.NONE.equals(currentLanguage)) {
            try {
                Log.debug(
                        "Loading '" + currentLanguage + "' language YAML dictionary file: '" +
                            languageFile + "'"
                );
                languageData = new ObjectMapper(new YAMLFactory()).readValue(languageFile, LanguageData.class);
                dictionary = languageData.getDictionary();
                Log.debug("YAML language dictionary successfully loaded");
            } catch (IOException | IllegalArgumentException e) {
                throw new FrameworkException(
                        "Not possible to read from language YML files. Check that language YML dictionary files " +
                        "are accessible in the project module's 'src" + File.separator + "main" + File.separator +
                        "resources" + File.separator + appInstance.getApplicationName() + File.separator +
                        LANGUAGE_FOLDER + "' folder.",
                        e
                );
            }
        } else {
            Log.debug("No language parameter acquired, skipping translation file lookup");
        }
    }

    public void init(String language) {
        currentLanguage = getLanguage(language);
        init(currentLanguage);
    }

    private URL loadLanguageFile() {
        if (!Language.NONE.equals(currentLanguage)) {
            return getClass()
                    .getClassLoader()
                    .getResource(
                            appInstance.getApplicationName() + File.separator +
                                  "lang" + File.separator +
                                  currentLanguage.toString().toLowerCase() + ".yml"
                    );
        }
        return null;
    }

    public Map<String, String> getDictionary() {
        return languageData.getDictionary();
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public String getTranslation(Object index) {
        try {
            return dictionary.get(index.toString());
        } catch (NullPointerException e) {
            throw new FrameworkException("Given '" + index +  "' key has not been found in the dictionary file");
        }
    }

    public Language getLanguage(String language) {
        if (language == null || "".equals(language)) {
            return Language.NONE;
        } else {
            try {
                return Language.valueOf(language.toUpperCase());
            } catch (Exception e) {
                throw new FrameworkException(
                        "Language '" + language + "' could not be found, please enter correct value " +
                        "according to ISO 639-1"
                );
            }
        }
    }

    public void changeLanguage(Language language) {
        init(language);
    }
}
