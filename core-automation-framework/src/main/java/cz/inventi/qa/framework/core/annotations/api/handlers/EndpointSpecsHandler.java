package cz.inventi.qa.framework.core.annotations.api.handlers;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;

import java.lang.annotation.Annotation;

/**
 * Handler for EndpointSpecs annotation.
 */
public class EndpointSpecsHandler implements EndpointSpecs {
    private String url;
    private boolean parameter;

    public EndpointSpecsHandler(String url, boolean isParameter) {
        this.url = url;
        this.parameter = isParameter;
    }

    @Override
    public String url() {
        return null;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
