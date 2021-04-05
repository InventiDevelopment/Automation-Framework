package cz.inventi.qa.framework.core.data.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.data.app.Applications;
import cz.inventi.qa.framework.core.data.app.Environment;
import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.managers.AppManager;

import java.util.ArrayList;

public class AppConfigData {

    @JsonProperty
    private Applications applications;

    public Applications getApplications() {
        return applications;
    }
}
