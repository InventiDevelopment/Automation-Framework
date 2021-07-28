package cz.inventi.qa.framework.core.objects.variables.web;

import cz.inventi.qa.framework.core.data.enums.Language;
import cz.inventi.qa.framework.core.data.enums.web.Browser;
import cz.inventi.qa.framework.core.data.enums.web.WebMandatoryParameters;
import cz.inventi.qa.framework.core.objects.parameters.TestSuiteParameters;
import cz.inventi.qa.framework.core.utils.Utils;

/**
 * Class to store parameters designed solely for web tests runs.
 * Also provides calls to TestSuiteParameters class.
 */
public class WebAppVariables {

    public String getEnvironment() {
        return TestSuiteParameters.getParameter(WebMandatoryParameters.ENVIRONMENT.name().toLowerCase());
    }

    public Language getLanguage() {
        return Utils.getEnum(
                Language.class,
                TestSuiteParameters.getParameter(WebMandatoryParameters.LANGUAGE.name().toLowerCase())
        );
    }

    public Browser getBrowser() {
        return Utils.getEnum(
                Browser.class,
                TestSuiteParameters.getParameter(WebMandatoryParameters.BROWSER.name().toLowerCase())
        );
    }
}
