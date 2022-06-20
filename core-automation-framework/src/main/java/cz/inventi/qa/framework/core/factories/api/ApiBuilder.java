package cz.inventi.qa.framework.core.factories.api;

import cz.inventi.qa.framework.core.objects.api.ApiObject;
import cz.inventi.qa.framework.core.objects.api.AOProps;


public class ApiBuilder {

    public static <T extends ApiObject> void initApiObjects(T apiObject, AOProps parentProps) {
        ApiObjectFactory.initApiObjects(apiObject, parentProps);
    }
}
