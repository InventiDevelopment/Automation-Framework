package cz.inventi.qa.framework.core.objects.test.steps;

import cz.inventi.qa.framework.core.objects.api.Endpoint;
import cz.inventi.qa.framework.core.objects.web.WebPage;
import cz.inventi.qa.framework.core.utils.AppInstanceUtils;

public abstract class StepsBase {

    /**
     * Shorthand call to create web AppInstance for given test scope.
     * @param startingWebPage WebPage app root class
     * @param <T> WebPage app root class type
     * @return Initialized WebPage for given AppInstance
     */
    public static <T extends WebPage> T getWebAppInstanceOf(Class<T> startingWebPage) {
        return AppInstanceUtils.getWebAppInstanceOf(startingWebPage);
    }

    /**
     * Shorthand call to create API AppInstance for given test scope.
     * @param api Api app root class
     * @param <T> Api app root class type
     * @return Initialized Api for given AppInstance
     */
    public static <T extends Endpoint<?>> T getApiAppInstanceOf(Class<T> api) {
        return AppInstanceUtils.getApiAppInstanceOf(api);
    }
}
