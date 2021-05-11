package cz.inventi.qa.framework.core.managers;

import cz.inventi.qa.framework.core.factories.web.webelement.WebElementLocator;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

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

    public static void addWebAppScreenshot(String description, WebElementLocator webElementLocator, AppInstance appInstance) {
        boolean shouldTakeScreenshots = appInstance.getConfigManager().getWebDriverConfigData().getGeneralSettings().takeScreenshots();
        WebDriver driver = appInstance.getWebDriverManager().getDriver();
        if (shouldTakeScreenshots) {
            Allure.addAttachment(description + " '" + webElementLocator.getXpath() + "'",
                    new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        }
    }
}
