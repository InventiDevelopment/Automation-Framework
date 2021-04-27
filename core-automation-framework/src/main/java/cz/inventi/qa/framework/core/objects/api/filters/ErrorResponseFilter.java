package cz.inventi.qa.framework.core.objects.api.filters;

import cz.inventi.qa.framework.core.Log;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ErrorResponseFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
        Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
        if (response.statusCode() >= 400) {
            Log.fail(filterableRequestSpecification.getMethod() + " " + filterableRequestSpecification.getURI() + " => " +
                    response.getStatusCode() + " " + response.getStatusLine());
        }
        return response;
    }
}
