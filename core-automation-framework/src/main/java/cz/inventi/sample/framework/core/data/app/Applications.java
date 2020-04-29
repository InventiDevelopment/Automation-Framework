package cz.inventi.sample.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Applications {
    @JsonProperty
    private Map<String, Environments> applications;

    public Map<String, Environments> getApplications() {
        return applications;
    }
}
