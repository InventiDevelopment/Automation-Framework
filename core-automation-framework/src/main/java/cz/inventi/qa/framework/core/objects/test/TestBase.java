package cz.inventi.qa.framework.core.objects.test;

import cz.inventi.qa.framework.core.data.enums.RunMode;
import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.framework.Log;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.utils.Utils;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class TestBase {

    /**
     * Sets all the parameters supplied through TestNG suite
     * or Maven command to the TestSuiteParameters class.
     * Also checks for @Secret parameters that should be
     * hidden in the output.
     * @param context TestNG context
     */
    @BeforeTest(alwaysRun = true)
    public void loadTestSuiteParameters(ITestContext context) {
        XmlTest currentTest = context.getCurrentXmlTest();
        Map<String, String> parameters = currentTest.getAllParameters();
        for (String key : parameters.keySet()) {
            String parameterValue = parameters.get(key);
            if (parameterValue.startsWith("$")) {
                String newValue = currentTest.getParameter(parameterValue.substring(1));
                parameters.put(key, newValue);
            }
        }
        TestSuiteParameters.setParameters(parameters);
    }

    /**
     * Quit app instances for given test class after all @Tests
     * have finished.
     */
    @AfterClass(alwaysRun = true)
    public void quit() {
        FrameworkManager.quitTestAppInstances(Utils.getTestIdentifier());
    }

    /**
     * Masks "secret" and "password" named parameters
     * entered via the TestNG XML suite from Allure
     * report files. Turned off only for the DEBUG mode.
     */
    // TODO reimplement better solution with AspectJ or Allure and use @Secret annotation
    @AfterSuite(alwaysRun = true)
    public void hideSecretParametersInAllure() {
        if (!RunMode.DEBUG.equals(FrameworkManager.getRunMode())) {
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
                            Matcher fileTypeMatch = fileTypeReg.matcher(fileType);
                            return fileTypeMatch.find();
                        } catch (IOException e) {
                            throw new FrameworkException("Could not get file type to mask parameter data", e);
                        }
                    }).forEach(file -> {
                        try {
                            String content = Files.readString(file.toPath());
                            Files.writeString(
                                    file.toPath(),
                                    content.replaceAll(
                                            TestSuiteParameters.getMaskedValuesRegex(),
                                            "[**MASKED**]"
                                    )
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