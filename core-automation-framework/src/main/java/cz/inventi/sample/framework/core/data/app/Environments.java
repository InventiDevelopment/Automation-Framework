package cz.inventi.sample.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Environments {
    @JsonProperty
    private Map<String, String> environments;

    public Map<String, String> getEnvironments() {
        return environments;
    }
}
