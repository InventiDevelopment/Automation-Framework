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
        return FrameworkManager.getInstance().initApiAppAt(api, Utils.getTestIdentifier());
    }

    /**
     * Creates web AppInstance for given test scope.
     * @param webPageClass WebPage class that has/inherits @Application annotation
     * @param <T> WebPage app root class type
     * @return Initialized WebPage for given AppInstance
     */
    public static <T extends WebPage> T getWebAppInstanceOf(Class<T> webPageClass) {
        return FrameworkManager.getInstance().initWebAppAt(webPageClass, Utils.getTestIdentifier());
    }
}
