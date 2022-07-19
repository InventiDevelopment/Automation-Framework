package cz.inventi.qa.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationConfig {

    @JsonProperty
    private WebApplication web;
    @JsonProperty
    private MobileApplication mobile;
    @JsonProperty
    private DesktopApplication desktop;
    @JsonProperty
    private ApiApplication api;

    public WebApplication getWeb() {
        return web;
    }

    public MobileApplication getMobile() {
        return mobile;
    }

    public DesktopApplication getDesktop() {
        return desktop;
    }

    public ApiApplication getApi() {
        return api;
    }
}
