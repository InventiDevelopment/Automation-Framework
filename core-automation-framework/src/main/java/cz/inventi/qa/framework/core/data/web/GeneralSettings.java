package cz.inventi.qa.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralSettings extends WebDriverSetting {
    private Wait wait;
    private WindowSize windowSize;

    @JsonProperty
    private String customTargetPath;
    @JsonProperty
    private boolean takeScreenshots;

    public Wait getWait() {
        return wait;
    }

    public String getCustomTargetPath() {
        return customTargetPath;
    }

    public WindowSize getWindowSize() {
        return windowSize;
    }

    public boolean takeScreenshots() {
        return takeScreenshots;
    }
}
