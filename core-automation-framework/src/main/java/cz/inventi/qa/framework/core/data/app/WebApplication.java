package cz.inventi.qa.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebApplication extends Application {

    @JsonProperty
    private boolean relaxedHttpsValidation;

    public boolean isRelaxedHttpsValidation() {
        return relaxedHttpsValidation;
    }
}
