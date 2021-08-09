package cz.inventi.qa.framework.core.utils;

import cz.inventi.qa.framework.core.managers.FrameworkManager;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.web.WebPage;

public class AppInstanceUtils {

    /**
     * Creates API AppInstance for given test scope.
     * @param api Api app root class
     * @param <T> Api app root class type
     * @return Initialized Api for given AppInstance
     */
    public static <T extends Api> T getApiAppInstanceOf(Class<T> api) {
        return FrameworkManager.getInstance().initApiAppAt(api, Utils.getCallerTestClassName());
    }

    /**
     * Creates web AppInstance for given test scope.
     * @param startingWebPage WebPage app root class
     * @param <T> WebPage app root class type
     * @return Initialized WebPage for given AppInstance
     */
    public static <T extends WebPage> T getWebAppInstanceOf(Class<T> startingWebPage) {
        return FrameworkManager.getInstance().initWebAppAt(startingWebPage, Utils.getCallerTestClassName());
    }
}
