package cz.inventi.qa.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Timeouts {

    @JsonProperty
    private int min;
    @JsonProperty
    private int mid;
    @JsonProperty
    private int max;

    public int getMin() {
        return min;
    }

    public int getMid() {
        return mid;
    }

    public int getMax() {
        return max;
    }
}
