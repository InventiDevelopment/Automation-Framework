package cz.inventi.qa.framework.core.factories.api;

import cz.inventi.qa.framework.core.annotations.api.ApiAuth;
import cz.inventi.qa.framework.core.annotations.api.EndpointSpecs;
import cz.inventi.qa.framework.core.data.enums.api.ApiAuthMethod;
import cz.inventi.qa.framework.core.objects.api.*;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class ApiObjectFactory {

    public static <T extends ApiObject> void initApiObjects(T apiObject, AOProps parentProps) {
        initParentApiObjectFields(apiObject, parentProps);
        initChildApiObjects(apiObject.getClass(), apiObject, parentProps);
    }

    private static <T extends ApiObject, Y extends ApiObject> void initChildApiObjects(
            Class<Y> parentEndpointClass,
            T apiObject,
            AOProps parentProps
    ) {
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

    private static <T extends ApiObject> void assignChildAOField(
            Field childAOField,
            T parentApiObject,
            AOProps parentProps
    ) {
        T childApiObject = ApiObjectFactory.reflectionInitAOClass(
                (Class<T>) childAOField.getType(),
                new Class<?>[]{AOProps.class},
                new Object[]{createAOProps(childAOField, parentApiObject, parentProps)}
        );
        try {
            childAOField.setAccessible(true);
            childAOField.set(parentApiObject, childApiObject);
        } catch (IllegalAccessException e) {
            throw new FrameworkException(
                    "Could not assign ApiObject field object '" + childAOField.getType().getName() + "'.",
                    e
            );
        }
    }

    private static <T extends ApiObject> AOProps createAOProps(
            Field f,
            T parentApiObject,
            AOProps parentProps
    ) {
        return new AOProps(
                getEndpointUrl(f),
                parentApiObject,
                parentProps,
                parentProps.getAppInstance(),
                getAuthMethod(f, parentProps)
        );
    }

    private static <T extends ApiObject> ApiAuthMethod getAuthMethod(Field f, AOProps parentProps) {
        if (Endpoint.class.isAssignableFrom(f.getType())) {
            ApiAuth fieldApiAuth = f.getType().getDeclaredAnnotation(ApiAuth.class);

            if (fieldApiAuth != null) {
                return fieldApiAuth.authType();
            }
        }
        return parentProps.getAuthMethod();
    }

    private static String getEndpointUrl(Field f) {
        if (Endpoint.class.isAssignableFrom(f.getType())) {
            EndpointSpecs endpointSpecs = f.getType().getAnnotation(EndpointSpecs.class);

            if (endpointSpecs == null) {
                throw new FrameworkException("Class '" + f.getType() + "' is missing @EndpointSpecs annotation");
            }
            return Objects.requireNonNull(endpointSpecs).url();
        }
        return "";
    }

    private static <T extends ApiObject> void initParentApiObjectFields(T apiObject, AOProps parentProps) {
        initEndpoints(apiObject, parentProps);
    }

    private static <T extends ApiObject> void initEndpoints(T apiObject, AOProps parentProps) {
        if (Endpoint.class.isAssignableFrom(apiObject.getClass().getSuperclass())) {
            Class<T> parentClass = (Class<T>) apiObject.getClass().getSuperclass();
            while (!parentClass.equals(Api.class) && !parentClass.equals(Endpoint.class)) {
                initChildApiObjects(parentClass, apiObject, parentProps);
                parentClass = (Class<T>) parentClass.getSuperclass();
            }
        }
    }

    public static <T extends Api> T initApi(Class<T> apiClass, ApiAppInstance<?> appInstance) {
        String appUrl = appInstance
                .getConfigManager()
                .getCurrentApplicationEnvironmentUrl();
        AOProps aoProps = new AOProps(
                appUrl,
                null,
                null,
                appInstance,
                getAuthMethod(apiClass)
        );
        T api = reflectionInitAOClass(apiClass, new Class[] {AOProps.class}, new Object[] {aoProps});
        api.setBaseUrl(appUrl);
        return api;
    }

    private static <T extends ApiObject> ApiAuthMethod getAuthMethod(Class<T> apiObjectClass) {
        ApiAuth apiAuth = apiObjectClass.getDeclaredAnnotation(ApiAuth.class);

        if (apiAuth != null) {
            return apiAuth.authType();
        } else {
            return ApiAuthMethod.NONE;
        }
    }

    public static <T extends ApiObject> T reflectionInitAOClass(
            Class<T> klass,
            Class<?>[] constructorArgs,
            Object[] constructorParams
    ) {
        if (constructorParams == null) constructorParams = new Object[0];
        if (constructorArgs == null) constructorArgs = new Class<?>[0];
        try {
            Constructor<?> klassConstructor = klass.getConstructor(constructorArgs);
            klassConstructor.setAccessible(true);
            return (T) klassConstructor.newInstance(constructorParams);
        } catch (
                NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e
        ) {
            throw new FrameworkException("Could not reflectively initialize " + klass + ".\n", e);
        }
    }

    public static <T extends ApiObject> T reflectionInitClassWithProps (Class<T> klass, AOProps props) {
        return reflectionInitAOClass(klass, new Class[] {AOProps.class}, new Object[] {props});
    }
}
