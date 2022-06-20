package cz.inventi.qa.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WindowSizeDimensions extends WebDriverSetting {

    @JsonProperty(required = true)
    private int width;
    @JsonProperty(required = true)
    private int height;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
