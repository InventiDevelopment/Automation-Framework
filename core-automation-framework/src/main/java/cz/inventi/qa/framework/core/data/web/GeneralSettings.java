package cz.inventi.qa.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralSettings extends WebDriverSetting {
    private Wait wait;

    @JsonProperty
    private String customTargetPath;
    @JsonProperty
    private String windowSize;
    @JsonProperty
    private String runMode;

    public Wait getWait() {
        return wait;
    }

    public String getCustomTargetPath() {
        return customTargetPath;
    }

    public String getWindowSize() {
        return windowSize;
    }

    public String getRunMode() {
        return runMode;
    }
}
