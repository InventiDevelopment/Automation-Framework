package cz.inventi.qa.framework.core.objects.api;

import cz.inventi.qa.framework.core.utils.ApiUtils;

public abstract class JsonDto {

    public String json() {
        return ApiUtils.convertToJson(this);
    }
}

