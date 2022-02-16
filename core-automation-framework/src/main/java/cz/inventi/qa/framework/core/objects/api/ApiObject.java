package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.annotations.api.handlers.EndpointSpecsHandler;
import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
import cz.inventi.qa.framework.core.factories.api.ApiBuilder;
import cz.inventi.qa.framework.core.objects.AbstractTestObject;

public class ApiObject extends AbstractTestObject {
    private final AOProps props;

    public ApiObject(AOProps props) {
        this.props = props;
        ApiBuilder.initApiObjects(this, props);
    }

    public AOProps getProps() {
        return props;
    }

    public EndpointSpecs getEndpointSpecsAnnotation () {
        EndpointSpecs endpointSpecs = getClass().getAnnotation(EndpointSpecs.class);

        if (endpointSpecs == null) {
            return new EndpointSpecsHandler("", false);
        }
        return getClass().getAnnotation(EndpointSpecs.class);
    }

    public ApiAuthMethod getAuthMethod() {
        return props.getAuthMethod();
    }
}
