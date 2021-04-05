package cz.inventi.qa.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.data.enums.ApiProtocolType;

public class ApiApplication extends Application {

    @JsonProperty
    private ApiProtocolType protocol;

    public ApiProtocolType getProtocol() {
        return protocol;
    }
}
