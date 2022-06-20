package cz.inventi.qa.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Environment {

    @JsonProperty
    private String url;
    @JsonProperty
    private String name;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
