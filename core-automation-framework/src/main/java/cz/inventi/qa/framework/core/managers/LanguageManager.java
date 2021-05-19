package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.utils.WebUtils;
import cz.inventi.qa.framework.core.data.config.LanguageData;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class LanguageManager {
    private static final String LANGUAGE_DIRECTORY = "lang/";
    private static LanguageManager languageManager;
    private Language currentLanguage;
    private String languageFile;
    private LanguageData languageData;
    private Map<String, String> dictionary;

    public static LanguageManager getInstance() {
        if (languageManager == null) languageManager = new LanguageManager();
        return languageManager;
    }

    public LanguageManager() {
        init(TestSuiteParameters.getParameter("language"));
    }

    public void init(Language language) {
        languageFile = loadLanguageFile();
        currentLanguage = language;

        if (!Language.NONE.equals(currentLanguage)) {
            try {
                Log.debug("Loading '" + currentLanguage + "' language YAML dictionary file: '" + languageFile + "'");
                languageData = new ObjectMapper(new YAMLFactory()).readValue(new File(languageFile), LanguageData.class);
                dictionary = languageData.getDictionary();
                Log.debug("YAML language dictionary successfully loaded");
            } catch (IOException e) {
                Log.fail("Not possible to read from language YML files. Check that files are accessible on following locations:\n '" + languageFile + "'", e);
            }
        } else {
            Log.debug("No language parameter acquired, skipping translation file lookup");
        }
    }

    public void init(String language) {
        currentLanguage = getLanguage(language);
        init(currentLanguage);
    }

    private String loadLanguageFile() {
        if (!Language.NONE.equals(currentLanguage)) {
            return WebUtils
                    .getFilePathDecoded(
                            Objects.requireNonNull(LanguageManager.class
                                    .getClassLoader()
                                    .getResource(LANGUAGE_DIRECTORY + currentLanguage.toString().toLowerCase() + ".yml"))
                                    .getPath());
        }
        return "";
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
            throw new RuntimeException("Given '" + index +  "' key has not been found in the dictionary file");
        }
    }

    public Language getLanguage(String language) {
        if (language == null || "".equals(language)) {
            return Language.NONE;
        } else {
            try {
                return Language.valueOf(language.toUpperCase());
            } catch (Exception e) {
                throw new RuntimeException("Language '" + language + "' could not be found, please enter correct value according to ISO 639-1");
            }
        }
    }

    public void changeLanguage(Language language) {
        init(language);
    }
}
