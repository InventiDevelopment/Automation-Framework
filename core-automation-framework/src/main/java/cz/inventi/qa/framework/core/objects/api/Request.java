package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.data.enums.HttpRequestType;

import java.util.Map;

public class Request {
    private HttpRequestType requestType;
    private Map<String, String> headerParams;
    private String body;
}
