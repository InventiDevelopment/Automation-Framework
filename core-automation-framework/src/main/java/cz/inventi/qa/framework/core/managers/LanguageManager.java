package cz.inventi.qa.framework.core.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cz.inventi.qa.framework.core.data.config.LanguageData;
import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.framework.core.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class LanguageManager {
    private static final String LANGUAGE_FOLDER = "lang";
    private final String applicationName;
    private Language currentLanguage;
    private LanguageData languageData;
    private Map<String, String> dictionary;

    public LanguageManager(String applicationName) {
        this.applicationName = applicationName;
        init(TestSuiteParameters.getParameter("language"));
    }

    public static <T extends WebPage> LanguageManager getLanguageManager(String applicationName) {
        return FrameworkManager
                .getCurrentTestRun()
                .getWebAppInstances()
                .get(applicationName)
                .getLanguageManager();
    }

    public static <T extends WebPage> String getTranslation(Enum<?> keyword, T webAppClass) {
        return getLanguageManager(webAppClass.getAppInstance().getApplicationName()).getTranslation(keyword);
    }

    public void init(Language language) {
        URL languageFile = loadLanguageFile();
        currentLanguage = language;
        if (!Language.NONE.equals(currentLanguage)) {
            String languageDirPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator +
                    applicationName + File.separator + LANGUAGE_FOLDER;
            try {
                Log.debug(
                        "Loading '" + currentLanguage + "' language YAML dictionary file: '" +
                            languageFile + "'"
                );
                languageData = new ObjectMapper(new YAMLFactory()).readValue(languageFile, LanguageData.class);
                dictionary = languageData.getDictionary();
                Log.debug("YAML language dictionary successfully loaded");
            } catch (MismatchedInputException e) {
                throw new FrameworkException(
                        "Language dictionary file (" + currentLanguage.forJackson() + ".yml) " +
                        "located at '" + languageDirPath +  "' does not have proper structure.",
                        e
                );
            } catch (IOException | IllegalArgumentException e) {
                throw new FrameworkException(
                        "Not possible to read from language YML file. Check that language YML dictionary file (" +
                        currentLanguage.forJackson() + ".yml) is accessible in the project module's '" +
                        languageDirPath + "' folder.",
                        e
                );
            }
        } else {
            Log.debug("No language parameter acquired, skipping translation file lookup.");
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
                            applicationName + File.separator + "lang" + File.separator +
                                  currentLanguage.forJackson() + ".yml"
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

    public String getTranslation(Enum<?> keyword) {
        try {
            return dictionary.get(keyword.toString());
        } catch (NullPointerException e) {
            throw new FrameworkException("Given '" + keyword +  "' key has not been found in the dictionary file.");
        }
    }

    public Language getLanguage(String language) {
        if (language == null || "".equals(language)) {
            return Language.NONE;
        } else {
            try {
                return Utils.getEnum(Language.class, language);
            } catch (Exception e) {
                throw new FrameworkException(
                        "Language '" + language + "' could not be found, please enter correct value " +
                        "according to ISO 639-1."
                );
            }
        }
    }

    public void changeLanguage(Language language) {
        init(language);
    }
}
