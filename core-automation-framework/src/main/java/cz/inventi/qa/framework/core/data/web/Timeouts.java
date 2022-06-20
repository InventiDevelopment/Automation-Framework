package cz.inventi.qa.framework.core.data.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Timeouts {
    private int min;
    private int mid;
    private int max;

    public Timeouts(@JsonProperty("min") int min,
                    @JsonProperty("mid") int mid,
                    @JsonProperty("max") int max) {
        this.min = min;
        this.mid = mid;
        this.max = max;
    }

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
