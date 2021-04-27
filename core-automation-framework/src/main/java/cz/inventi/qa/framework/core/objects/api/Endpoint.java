package cz.inventi.qa.framework.core.objects.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public abstract class Endpoint<T> extends ApiObject {

    public Endpoint(AOProps props) {
        super(props);
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

    @SuppressWarnings("unchecked")
    public T setUrlParameter(String urlParameter) {
        getProps().setParameterValue(URLEncoder.encode(urlParameter, StandardCharsets.UTF_8));
        return (T) this;
    }

    public String getUrlParameter() {
        return getProps().getParameterValue();
    }

    public abstract Object callGet();

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