package cz.inventi.qa.framework.core.objects.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.data.app.Environments;

import java.util.Map;

public class AppConfigData {

    @JsonProperty
    private Map<String, Environments> applications;

    public Map<String, Environments> getApplications() {
        return applications;
    }
}