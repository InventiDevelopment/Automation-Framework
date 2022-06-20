package cz.inventi.qa.framework.core.objects.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.data.enums.api.ApiMandatoryParameters;
import cz.inventi.qa.framework.core.factories.api.ApiObjectFactory;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;

public class ApiAppInstance<T extends Api> extends AppInstance<T> {
    private final RestAssuredManager<T> restAssuredManager;

    public ApiAppInstance(Class<T> applicationClass, String applicationName) {
        super(applicationClass, applicationName);
        checkMandatoryParametersAreSet(ApiMandatoryParameters.class);
        restAssuredManager = new RestAssuredManager<>(this);
        setRestAssuredConfiguration();
    }

    public T retrieveOrInitApi(Class<T> api) {
        if (getApplicationStartingClassInitialized() == null) {
            setApplicationStartingClassInitialized(ApiObjectFactory.initApi(api, this));
        }
        return getApplicationStartingClassInitialized();
    }

    private void setRestAssuredConfiguration() {
        /* Set Jackson mapping options for Rest Assured */
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
                new ObjectMapperConfig().jackson2ObjectMapperFactory(
                    (cls, charset) ->
                        new ObjectMapper().findAndRegisterModules()
                            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                )
        );
        /* Set logging for debug mode */
        if (FrameworkManager.getRunMode().equals(RunMode.DEBUG)) {
            RestAssured.requestSpecification = RestAssured.requestSpecification
                    .log()
                    .all()
                    .request();
        }
    }

    public RestAssuredManager<T> getRestAssuredManager() {
        return restAssuredManager;
    }

    public void quit() {}
}