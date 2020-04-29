package cz.inventi.sample.framework.core.objects.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class LanguageData {

    @JsonProperty
    private Map<String, String> dictionary;

    public Map<String, String> getDictionary() {
        return dictionary;
    }
}
