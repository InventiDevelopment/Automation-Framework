package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.data.enums.ApplicationType;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class ReportManager {
    private final AppInstance appInstance;

    public ReportManager(AppInstance appInstance) {
        this.appInstance = appInstance;
    }

    public void init() {
        ApplicationType applicationType = appInstance.getAppManager().getCurrentApplicationType();
        if (ApplicationType.API.equals(applicationType)) {
            RestAssured.requestSpecification = new RequestSpecBuilder().build().filter(new AllureRestAssured());
        }
    }
}
