package cz.inventi.qa.framework.core.factories.api;

import cz.inventi.qa.framework.core.Log;
import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.data.app.ApiApplication;
import cz.inventi.qa.framework.core.objects.api.AOProps;
import cz.inventi.qa.framework.core.objects.api.Api;
import cz.inventi.qa.framework.core.objects.api.ApiObject;
import cz.inventi.qa.framework.core.objects.api.Endpoint;
import cz.inventi.qa.framework.core.objects.framework.AppInstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Objects;


public class ApiObjectFactory {

    public static <T extends ApiObject> void initApiObjects(T apiObject, AOProps parentProps) {
        initParentApiObjectFields(apiObject, parentProps);
        initApiObjects(apiObject.getClass(), apiObject, parentProps);
    }

    private static <T extends ApiObject> void initApiObjects(Class<? extends ApiObject> parentEndpointClass, T apiObject, AOProps parentProps) {
        for (Field childField : parentEndpointClass.getDeclaredFields()) {
            if (!Modifier.toString(childField.getModifiers()).equals("")) {
                childField.setAccessible(true);
            }

            if (isRelatedAOField(childField)) {
                assignChildAOField(childField, apiObject, parentProps);
            }
        }
    }

    private static boolean isRelatedAOField(Field field) {
        return Endpoint.class.isAssignableFrom(field.getType());
    }

    private static <T extends ApiObject> void assignChildAOField(Field childAOField, T parentApiObject, AOProps parentProps) {
        T childApiObject = ApiObjectFactory.reflectionInitAOClass((Class<T>) childAOField.getType(), new Class<?>[]{AOProps.class}, new Object[]{createAOProps(childAOField, parentApiObject, parentProps)});

        try {
            childAOField.setAccessible(true);
            childAOField.set(parentApiObject, childApiObject);
        } catch (IllegalAccessException e) {
            Log.fail("Could not assign ApiObject field object '" + childAOField.getType().getName() + "'.", e);
        }
    }

    private static <T extends ApiObject> AOProps createAOProps(Field f, T parentApiObject, AOProps parentProps) {
        String endpointUrl = "";

        if (Endpoint.class.isAssignableFrom(f.getType())) {
            EndpointSpecs endpointSpecs = f.getType().getAnnotation(EndpointSpecs.class);

            if (endpointSpecs == null) {
                Log.fail("Class '" + f.getType() + "' is missing @EndpointSpecs annotation");
            }
            endpointUrl += Objects.requireNonNull(endpointSpecs).url();
        }

        return new AOProps(endpointUrl, parentApiObject, parentProps, parentProps.getAppInstance());
    }

    private static <T extends ApiObject> void initParentApiObjectFields(T apiObject, AOProps parentProps) {
        if (Endpoint.class.isAssignableFrom(apiObject.getClass().getSuperclass())) {
            Class<T> parentClass = (Class<T>) apiObject.getClass().getSuperclass();
            while (!parentClass.equals(Api.class) && !parentClass.equals(Endpoint.class)) {
                initApiObjects(parentClass, apiObject, parentProps);
                parentClass = (Class<T>) parentClass.getSuperclass();
            }
        }
    }

    public static <T extends Api> T initApi (Class<T> apiClass, AppInstance appInstance) {
        AOProps aoProps = new AOProps(appInstance.getAppManager().getAppUrl(), apiClass, null, appInstance);
        ApiApplication apiApplication = appInstance.getConfigManager().getAppsConfigData().getApplications().getApi().get(appInstance.getAppManager().getCurrentApplicationName());
        T api = reflectionInitAOClass(apiClass, new Class[] {AOProps.class}, new Object[] {aoProps});
        api.setBaseUrl(appInstance.getAppManager().getAppUrl());
        api.setApiProtocolType(apiApplication.getProtocol());
        return api;
    }

    public static <T extends ApiObject> T reflectionInitAOClass(Class<T> klass, Class<?>[] constructorArgs, Object[] constructorParams) {
        if (constructorParams == null) constructorParams = new Object[0];
        if (constructorArgs == null) constructorArgs = new Class<?>[0];

        try {
            Constructor<?> klassConstructor = klass.getConstructor(constructorArgs);
            klassConstructor.setAccessible(true);
            return (T) klassConstructor.newInstance(constructorParams);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not reflectively initialize " + klass + ".\n" + e);
        }
    }

    public static <T extends ApiObject> T reflectionInitClassWithProps (Class<T> klass, AOProps props) {
        return reflectionInitAOClass(klass, new Class[] {AOProps.class}, new Object[] {props});
    }
}
