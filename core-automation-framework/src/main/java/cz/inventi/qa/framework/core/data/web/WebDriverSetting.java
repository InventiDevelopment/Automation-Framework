package cz.inventi.qa.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebDriverSetting {

    @JsonProperty
    private String[] arguments;

    public String[] getArguments() {
        return arguments;
    }
}
