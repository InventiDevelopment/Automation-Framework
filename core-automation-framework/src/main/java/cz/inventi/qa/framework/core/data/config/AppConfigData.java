package cz.inventi.qa.framework.core.data.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.annotations.ConfigFileSpecs;
import cz.inventi.qa.framework.core.data.app.ApplicationConfig;

@ConfigFileSpecs(name = "appConfig.yml")
public class AppConfigData {

    @JsonProperty
    private ApplicationConfig application;

    public ApplicationConfig getApplicationConfig() {
        return application;
    }
}
