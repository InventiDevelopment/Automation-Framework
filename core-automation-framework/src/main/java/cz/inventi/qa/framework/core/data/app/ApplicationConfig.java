package cz.inventi.qa.framework.core.data.app;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ApplicationConfig {

    @JsonProperty
    private Map<String, WebApplication> web;
    @JsonProperty
    private Map<String, MobileApplication> mobile;
    @JsonProperty
    private Map<String, DesktopApplication> desktop;
    @JsonProperty
    private Map<String, ApiApplication> api;

    public Map<String, WebApplication> getWeb() {
        return web;
    }

    public Map<String, MobileApplication> getMobile() {
        return mobile;
    }

    public Map<String, DesktopApplication> getDesktop() {
        return desktop;
    }

    public Map<String, ApiApplication> getApi() {
        return api;
    }
}
