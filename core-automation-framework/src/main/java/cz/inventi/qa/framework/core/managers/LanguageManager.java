package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.WebUtils;
import cz.inventi.qa.framework.core.data.config.LanguageData;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class LanguageManager {
    private static final String LANGUAGE_DIRECTORY = "lang/";
    private final ObjectMapper mapper;
    private final AppInstance appInstance;
    private Language currentLanguage;
    private String languageFile;
    private LanguageData languageData;
    private Map<String, String> dictionary;

    public LanguageManager(AppInstance appInstance) {
        this(appInstance, Language.NONE);
    }

    public LanguageManager(AppInstance appInstance, Language language) {
        this.appInstance = appInstance;
        currentLanguage = language;
        mapper = new ObjectMapper(new YAMLFactory());
        languageFile = loadLanguageFile();
    }

    public void init(Language language) {
        appInstance.getParametersManager().getCommonParameters().setLanguage(language);
        languageFile = loadLanguageFile();
        currentLanguage = language;

        if (!currentLanguage.equals(Language.NONE)) {
            try {
                Log.debug("Loading '" + currentLanguage + "' language YAML dictionary file: '" + languageFile + "'");
                languageData = mapper.readValue(new File(languageFile), LanguageData.class);
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
        init(getLanguage(language));
    }

    private String loadLanguageFile() {
        if (!currentLanguage.equals(Language.NONE)) {
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
            throw new RuntimeException("Given '" + index +  "' key has not been found in the dictionary file.");
        }
    }

    public Language getLanguage(String language) {
        try {
            return Language.valueOf(language);
        } catch (Exception e) {
            throw new RuntimeException("Language '" + language + "' could not be found, please enter correct value according to ISO 639-1.");
        }
    }

    public void changeLanguage(Language language) {
        init(language);
    }
}
