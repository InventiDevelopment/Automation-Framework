package cz.inventi.qa.framework.core.objects.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.inventi.qa.framework.core.data.web.*;

public class DriverConfigData {
    private GeneralSettings generalSettings;

    @JsonProperty
    private Chrome chrome;
    @JsonProperty
    private InternetExplorer explorer;
    @JsonProperty
    private Opera opera;
    @JsonProperty
    private Firefox firefox;
    @JsonProperty
    private Edge edge;

    public GeneralSettings getGeneralSettings() {
        return generalSettings;
    }

    public Chrome getChrome() {
        return chrome;
    }

    public InternetExplorer getExplorer() {
        return explorer;
    }

    public Opera getOpera() {
        return opera;
    }

    public Firefox getFirefox() {
        return firefox;
    }

    public Edge getEdge() {
        return edge;
    }

    public boolean waitsAutomatically () {
        return generalSettings.getWait().waitsAutomatically();
    }
}
