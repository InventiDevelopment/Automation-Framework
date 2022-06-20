package cz.inventi.qa.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.data.enums.web.WindowSizeType;

public class WindowSize extends WebDriverSetting {

    @JsonProperty
    private WindowSizeType sizeType;
    @JsonProperty
    private WindowSizeDimensions dimensions;

    public WindowSizeType getSizeType() {
        return sizeType;
    }

    public WindowSizeDimensions getDimensions() {
        return dimensions;
    }
}
