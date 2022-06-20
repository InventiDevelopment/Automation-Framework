package cz.inventi.qa.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Application {

    @JsonProperty
    private Map<String, String> environments;

    public Map<String, String> getEnvironments() {
        return environments;
    }
}
