package cz.inventi.qa.framework.core.data.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.annotations.ConfigFileSpecs;
import cz.inventi.qa.framework.core.data.app.Applications;

@ConfigFileSpecs(name = "appsConfig.yml")
public class AppsConfigData {

    @JsonProperty
    private Applications applications;

    public Applications getApplications() {
        return applications;
    }
}
