package cz.inventi.qa.framework.core.objects.api;

public abstract class SoapEndpoint<T> extends Endpoint<T> {

    public SoapEndpoint(AOProps props) {
        super(props);
    }

    public Object callGet() {
        throw new RuntimeException("GET method not supported for SOAP API calls");
    }

    public Object callPost(String body) {
        throw new RuntimeException("POST method not supported for SOAP API calls");
    }

    public Object callPut() {
        throw new RuntimeException("PUT method not supported for SOAP API calls");
    }

    public Object callHead() {
        throw new RuntimeException("HEAD method not supported for SOAP API calls");
    }

    public Object callPatch() {
        throw new RuntimeException("PATCH method not supported for SOAP API calls");
    }

    public Object callDelete() {
        throw new RuntimeException("DELETE method not supported for SOAP API calls");
    }

    public Object callOptions() {
        throw new RuntimeException("OPTIONS method not supported for SOAP API calls");
    }

    public Object callTrace() {
        throw new RuntimeException("TRACE method not supported for SOAP API calls");
    }

    public Object callConnect() {
        throw new RuntimeException("CONNECT method not supported for SOAP API calls");
    }
}
