package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.objects.framework.FrameworkException;

import java.util.Map;

public abstract class SoapEndpoint<T> extends Endpoint<T> {

    public SoapEndpoint(AOProps props) {
        super(props);
    }

    @Override
    public Object callGet() {
        throw new FrameworkException("GET method not supported for SOAP API calls");
    }

    @Override
    public Object callPost(String body) {
        throw new FrameworkException("POST method not supported for SOAP API calls");
    }

    @Override
    public Object callHead() {
        throw new FrameworkException("HEAD method not supported for SOAP API calls");
    }

    @Override
    public Object callDelete() {
        throw new FrameworkException("DELETE method not supported for SOAP API calls");
    }

    @Override
    public Object callOptions() {
        throw new FrameworkException("OPTIONS method not supported for SOAP API calls");
    }

    @Override
    public Object callTrace() {
        throw new FrameworkException("TRACE method not supported for SOAP API calls");
    }

    @Override
    public Object callConnect() {
        throw new FrameworkException("CONNECT method not supported for SOAP API calls");
    }

    @Override
    public Object callGet(Map<String, Object> queryParams) {
        throw new FrameworkException("GET method not supported for SOAP API calls");
    }

    @Override
    public Object callPut(String body) {
        throw new FrameworkException("PUT method not supported for SOAP API calls");
    }

    @Override
    public Object callPatch(String body) {
        throw new FrameworkException("PATCH method not supported for SOAP API calls");
    }

    @Override
    public Object createRequest() {
        throw new FrameworkException("Creating request not implemented for SOAP API calls");
    }

    @Override
    public Object createRequestWithAuth() {
        throw new FrameworkException("Creating request with auth not implemented for SOAP API calls");
    }
}
