package cz.inventi.qa.framework.core.objects.test.steps;

import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.framework.core.utils.Utils;

public abstract class StepsBase {

    public static <T extends WebPage> T getWebAppInstanceOf(Class<T> startingWebPage) {
        return FrameworkManager.getInstance().initWebAppAt(startingWebPage, Utils.getCallerTestClassName());
    }

    public static <T extends Api> T getApiAppInstanceOf(Class<T> api) {
        return FrameworkManager.getInstance().initApiAppAt(api, Utils.getCallerTestClassName());
    }
}
