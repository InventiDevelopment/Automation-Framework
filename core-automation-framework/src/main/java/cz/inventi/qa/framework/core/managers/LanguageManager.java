package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.Utils;
import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.config.LanguageData;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class LanguageManager {
    private static final String LANGUAGE_DIRECTORY = "lang/";
    private static Language currentLanguage = ParametersManager.getLanguage();
    private static final String LANGUAGE_FILE_PATH = Utils.getFilePathDecoded(Objects.requireNonNull(LanguageManager.class.getClassLoader().getResource(LANGUAGE_DIRECTORY + currentLanguage.toString().toLowerCase() + ".yml")).getPath());
    private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private static LanguageData languageData;
    private static Map<String, String> dictionary;

    public static void init () {
        try {
            Log.debug("Loading language YAML dictionary file: '" + LANGUAGE_FILE_PATH + "'");
            languageData = mapper.readValue(new File(LANGUAGE_FILE_PATH), LanguageData.class);
            dictionary = languageData.getDictionary();
            Log.debug("YAML language dictionary successfully loaded.");
        } catch (IOException e) {
            Log.fail("Not possible to read from language YML files. Check that files are accessible on following locations:\n '" + LANGUAGE_FILE_PATH + "'", e);
        }
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
}
