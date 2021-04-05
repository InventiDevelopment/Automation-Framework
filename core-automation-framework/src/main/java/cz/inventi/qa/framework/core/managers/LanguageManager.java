package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.Utils;
import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.data.config.LanguageData;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class LanguageManager {
    private static final String LANGUAGE_DIRECTORY = "lang/";
    private static Language currentLanguage = ParametersManager.getCommonParameters().getLanguage();
    private static String languageFile = loadLanguageFile();
    private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private static LanguageData languageData;
    private static Map<String, String> dictionary;

    public static void init () {
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

    private static String loadLanguageFile() {
        if (!currentLanguage.equals(Language.NONE)) {
            return Utils.getFilePathDecoded(Objects.requireNonNull(LanguageManager.class.getClassLoader().getResource(LANGUAGE_DIRECTORY + currentLanguage.toString().toLowerCase() + ".yml")).getPath());
        }
        return "";
    }

    public static Map<String, String> getDictionary() {
        return languageData.getDictionary();
    }

    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static String getTranslation(Object index) {
        try {
            return dictionary.get(index.toString());
        } catch (NullPointerException e) {
            throw new RuntimeException("Given '" + index +  "' key has not been found in the dictionary file.");
        }
    }

    public static void changeLanguage(Language language) {
        ParametersManager.getCommonParameters().setLanguage(language);
        currentLanguage = language;
        languageFile = loadLanguageFile();
        init();
    }
}
