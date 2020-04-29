package cz.inventi.sample.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Wait {

    @JsonProperty
    private boolean waitsAutomatically;
    @JsonProperty
    private Timeouts timeouts;

    public boolean waitsAutomatically() {
        return waitsAutomatically;
    }

    public Timeouts getTimeouts() {
        return timeouts;
    }

}
