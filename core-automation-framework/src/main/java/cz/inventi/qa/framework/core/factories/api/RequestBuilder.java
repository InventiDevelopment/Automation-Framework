package cz.inventi.qa.framework.core.factories.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class RequestBuilder {

    public static Response get(String url) {
        return RestAssured.get(url);
    }

    public static <T extends Object> T get(String url, Class<T> returnObject) {
        return RestAssured.get(url).as(returnObject);
    }
}
