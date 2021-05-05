package cz.inventi.qa.framework.core.managers;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class ReportManager {
    private static ReportManager reportManager;

    public static ReportManager getInstance() {
        if (reportManager == null) init();
        return reportManager;
    }

    public static void init() {
        reportManager = new ReportManager();
        RestAssured.requestSpecification = new RequestSpecBuilder().build().filter(new AllureRestAssured());
    }
}
