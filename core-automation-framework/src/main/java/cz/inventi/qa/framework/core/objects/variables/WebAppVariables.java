package cz.inventi.qa.framework.core.objects.variables;

import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.data.enums.web.Browser;
import cz.inventi.qa.framework.core.data.enums.web.WebMandatoryParameters;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;

/**
 * Class to store parameters designed solely for web tests runs.
 * Also provides calls to TestSuiteParameters class.
 */
public class WebAppVariables {

    public String getEnvironment() {
        return TestSuiteParameters.getParameter(WebMandatoryParameters.ENVIRONMENT.name());
    }

    public Language getLanguage() {
        return Language.valueOf(TestSuiteParameters.getParameter(WebMandatoryParameters.LANGUAGE.name()).toUpperCase());
    }

    public Browser getBrowser() {
        return Browser.valueOf(TestSuiteParameters.getParameter(WebMandatoryParameters.BROWSER.name()).toUpperCase());
    }
}
