package cz.inventi.qa.framework.core.objects.api.filters;

import cz.inventi.qa.framework.core.managers.FrameworkManager;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.ProxySpecification;

public class ProxyFilter implements Filter {

    public ProxyFilter() {
    }

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext ctx
    ) {
        if (FrameworkManager.getProxySettings() != null) requestSpec.proxy(retrieveProxyRequestSpec());
        return ctx.next(requestSpec, responseSpec);
    }

    public ProxySpecification retrieveProxyRequestSpec() {
        String proxyServer = FrameworkManager.getProxySettings().getProxyServer();
        String proxyUser = FrameworkManager.getProxySettings().getProxyUser();
        String proxyPass = FrameworkManager.getProxySettings().getProxyPass();
        String proxyScheme = FrameworkManager.getProxySettings().getProxyScheme().name().toLowerCase();
        int proxyPort = FrameworkManager.getProxySettings().getProxyPort();
       ProxySpecification raProxySpecification  = ProxySpecification
                .host(proxyServer)
                .withPort(proxyPort)
                .withScheme(proxyScheme);
        if (proxyUser != null && proxyPass != null) {
            raProxySpecification = raProxySpecification.withAuth(proxyUser, proxyPass);
        }
        return raProxySpecification;
    }
}
