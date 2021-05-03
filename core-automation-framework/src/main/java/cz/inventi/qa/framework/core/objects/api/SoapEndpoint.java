package cz.inventi.qa.framework.core.objects.api;

import java.util.Map;

public abstract class SoapEndpoint<T> extends Endpoint<T> {

    public SoapEndpoint(AOProps props) {
        super(props);
    }

    @Override
    public Object callGet() {
        throw new RuntimeException("GET method not supported for SOAP API calls");
    }

    @Override
    public Object callPost(String body) {
        throw new RuntimeException("POST method not supported for SOAP API calls");
    }

    @Override
    public Object callHead() {
        throw new RuntimeException("HEAD method not supported for SOAP API calls");
    }

    @Override
    public Object callDelete() {
        throw new RuntimeException("DELETE method not supported for SOAP API calls");
    }

    @Override
    public Object callOptions() {
        throw new RuntimeException("OPTIONS method not supported for SOAP API calls");
    }

    @Override
    public Object callTrace() {
        throw new RuntimeException("TRACE method not supported for SOAP API calls");
    }

    @Override
    public Object callConnect() {
        throw new RuntimeException("CONNECT method not supported for SOAP API calls");
    }

    @Override
    public Object callGet(Map<String, Object> queryParams) {
        throw new RuntimeException("GET method not supported for SOAP API calls");
    }

    @Override
    public Object callPut(String body) {
        throw new RuntimeException("PUT method not supported for SOAP API calls");
    }

    @Override
    public Object callPatch(String body) {
        throw new RuntimeException("PATCH method not supported for SOAP API calls");
    }

    @Override
    public Object createRequest() {
        throw new RuntimeException("Creating request not implemented for SOAP API calls");
    }

    @Override
    public Object createRequestWithAuth() {
        throw new RuntimeException("Creating request with auth not implemented for SOAP API calls");
    }
}
