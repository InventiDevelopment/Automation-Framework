package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

@Listeners(TestListener.class)
public abstract class TestBase {

    /**
     * Sets all the parameters supplied through TestNG suite
     * or Maven command to the TestSuiteParameters class.
     * Also checks for @Secret parameters that should be
     * hidden in the output.
     */
    @BeforeTest(alwaysRun = true)
    public void loadTestSuiteParameters(ITestContext context) {
        TestSuiteParameters.parseTestSuiteParameters(context);
    }

    /**
     * Calls method of TestNG to display all collected
     * soft assertions for given application run through test class.
     */
    public void handleSoftAssertions() {
            FrameworkManager
                    .getCurrentTestRun()
                    .getSoftAssertCollector()
                    .printExceptions();
    }

    /**
     * Quit app instances for given test class after all @Tests
     * have finished.
     */
    @AfterClass(alwaysRun = true)
    public void quit() {
        if (FrameworkManager.getTestRuns().size() > 0) {
            handleSoftAssertions();
            FrameworkManager.quitCurrentTestRun();
        }
    }

    /**
     * Masks "secret" and "password" named parameters
     * entered via the TestNG XML suite from Allure
     * report files. Turned off only for the DEBUG mode.
     */
    // TODO reimplement better solution with AspectJ or Allure and use @Secret annotation
    @AfterSuite(alwaysRun = true)
    public void hideSecretParametersInAllure() {
        String maskedValuesRegex = TestSuiteParameters.getMaskedValuesRegex();
        if (!RunMode.DEBUG.equals(FrameworkManager.getRunMode()) && !maskedValuesRegex.equals("")) {
            Log.info("Masking secret values in ALlure's Results files");
            File allureResultsDir = new File(System.getProperty("allure.results.directory"));
            Arrays
                    .stream(Objects.requireNonNull(allureResultsDir.listFiles()))
                    .filter(file -> {
                        try {
                            Pattern fileTypeReg = Pattern.compile(
                                    "(xml|json|txt|html|htm)",
                                    Pattern.CASE_INSENSITIVE
                            );
                            String fileType = Files.probeContentType(file.toPath());
                            if (fileType == null) {
                                return false;
                            } else {
                                return fileTypeReg.matcher(fileType).find();
                            }
                        } catch (IOException e) {
                            throw new FrameworkException("Could not get file type to mask parameter data", e);
                        }
                    }).forEach(file -> {
                        try {
                            String content = Files.readString(file.toPath());
                            Files.writeString(
                                    file.toPath(),
                                    content.replaceAll(maskedValuesRegex,"[**MASKED**]")
                            );
                        } catch (IOException e) {
                            throw new FrameworkException("Could not mask parameter data", e);
                        }
                    });
        }
    }

    /**
     * Has to be called after the loadTestSuiteParameters()
     * method. Initialize steps needed for your tests here.
     * These steps will have access to the TestNG
     * parameters.
     */
    @BeforeClass
    public abstract void initSteps();
}