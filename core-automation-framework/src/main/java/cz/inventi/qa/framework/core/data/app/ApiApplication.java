package cz.inventi.qa.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiApplication extends Application {

    @JsonProperty
    private boolean relaxedHttpsValidation;

    public boolean isRelaxedHttpsValidation() {
        return relaxedHttpsValidation;
    }
}
