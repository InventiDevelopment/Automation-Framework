package cz.inventi.qa.framework.core.data.enums.api;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ApiMandatoryParameters {
    ENVIRONMENT;

    @JsonValue
    public String forJackson() {
        return name().toLowerCase();
    }
}
