package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
import cz.inventi.qa.framework.core.factories.api.ApiBuilder;

public class ApiObject {
    private final AOProps props;

    public ApiObject(AOProps props) {
        this.props = props;
        ApiBuilder.initApiObjects(this, props);
    }

    public AOProps getProps() {
        return props;
    }

    public ApiAuthMethod getAuthMethod() {
        return props.getAuthMethod();
    }
}
