package cz.inventi.qa.framework.core.data.enums.web;

import com.fasterxml.jackson.annotation.JsonValue;

public enum WebMandatoryParameters {
    ENVIRONMENT,
    BROWSER,
    LANGUAGE;

    @JsonValue
    public String forJackson() {
        return name().toLowerCase();
    }
}
