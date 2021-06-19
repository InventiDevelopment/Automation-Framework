package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.parameters.AuthParameters;
import cz.inventi.qa.framework.core.objects.variables.api.EndpointVariables;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class Endpoint<T> extends ApiObject {
    private final EndpointVariables endpointVariables;

    public Endpoint(AOProps props) {
        super(props);
        endpointVariables = new EndpointVariables();
    }

    public EndpointVariables getEndpointVariables() {
        return endpointVariables;
    }

    public String getFullUrlWithParams() {
        return getProps().getFullUrlWithParams();
    }

    public boolean isParameter() {
        return getProps().isParameter();
    }

    public String getEndpointUrl() {
        return getProps().getEndpointUrl();
    }

    public String getFullUrl() {
        return getProps().getFullUrl();
    }

    /**
     * Returns API authorization parameters from the global API level
     * or from the endpoint level if defined.
     * @return Authparameters object
     */
    public AuthParameters getAuthParameters() {
        if (endpointVariables.getAuthParameters() != null) {
            return endpointVariables.getAuthParameters();
        } else {
            return getProps().getAuthParameters();
        }
    }

    @SuppressWarnings("unchecked")
    public T setUrlParameter(String urlParameter) {
        if (urlParameter != null) urlParameter = URLEncoder.encode(urlParameter, StandardCharsets.UTF_8);
        getProps().setParameterValue(urlParameter);
        return (T) this;
    }

    public String getUrlParameter() {
        return getProps().getParameterValue();
    }

    public abstract Object callGet();

    public abstract Object callGet(Map<String, Object> queryParams);

    public abstract Object callPost(String body);

    public abstract Object callPut(String body);

    public abstract Object callHead();

    public abstract Object callPatch(String body);

    public abstract Object callDelete();

    public abstract Object callOptions();

    public abstract Object callTrace();

    public abstract Object callConnect();

    public abstract Object createRequest();

    public abstract Object createRequestWithAuth();

}