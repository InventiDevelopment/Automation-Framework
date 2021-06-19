package cz.inventi.qa.framework.core.data.enums.api;

import cz.inventi.qa.framework.core.utils.ApiUtils;

public abstract class JsonDto {

    public String json() {
        return ApiUtils.convertToJson(this);
    }
}

