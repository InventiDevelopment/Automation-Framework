package cz.inventi.qa.framework.core.factories.web.webobject;

import cz.inventi.qa.framework.core.annotations.web.FindElement;
import cz.inventi.qa.framework.core.annotations.web.NoParent;
import cz.inventi.qa.framework.core.factories.web.PageBuilder;
import cz.inventi.qa.framework.core.objects.framework.FrameworkException;
import cz.inventi.qa.framework.core.objects.web.WOProps;
import cz.inventi.qa.framework.core.objects.web.WebAppInstance;
import cz.inventi.qa.framework.core.objects.web.WebComponent;
import cz.inventi.qa.framework.core.objects.web.WebObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;


@SuppressWarnings("unchecked")
public class WebObjectFactory {

    public static <T extends WebObject> void initElements (T webObject, WOProps parentProps) {
        initParentWebObjectFields(webObject, parentProps);
        initElements(webObject.getClass(), webObject, parentProps);
    }

    private static <T extends WebObject> void initElements (
            Class<? extends WebObject> parentWebObjectClass,
            T webObject,
            WOProps parentProps
    ) {
        for (Field childComponentField : parentWebObjectClass.getDeclaredFields()) {
            if (!Modifier.toString(childComponentField.getModifiers()).equals("")) {
                childComponentField.setAccessible(true);
            }
            if (isRelatedWebComponentField(childComponentField)) {
                assignWebComponentField(childComponentField, webObject, parentProps);
            }
        }
    }

    private static boolean isRelatedWebComponentField(Field field) {
        return WebComponent.class.isAssignableFrom(field.getType());
    }

    private static <T extends WebObject> void assignWebComponentField(
            Field childComponentField,
            T parentWebObject,
            WOProps parentProps
    ) {
        T childWebComponent = WebObjectFactory.reflectionInitWOClass(
                (Class<T>) childComponentField.getType(),
                new Class<?>[]{WOProps.class},
                new Object[]{getWOProps(childComponentField, parentWebObject, parentProps)}
        );
        try {
            childComponentField.setAccessible(true);
            childComponentField.set(parentWebObject, childWebComponent);
        } catch (IllegalAccessException e) {
            throw new FrameworkException(
                    "Could not assign WebObject field object '" + childComponentField.getType().getName() + "'.",
                    e
            );
        }
    }

    private static <T extends WebObject> WOProps getWOProps (Field f, T parentWebObject, WOProps parentProps) {
        String finalXpath = "";
        if (!hasNoParentAnnotation(f)) {
            finalXpath = PageBuilder.generateXpathWithParent(
                    parentWebObject.getXpath(),
                    f.getType().getDeclaredAnnotation(FindElement.class)
            );
        }
        return new WOProps(finalXpath, parentWebObject, parentProps, parentProps.getAppInstance());
    }

    private static boolean hasNoParentAnnotation (Field f) {
        NoParent childNoParentAnnotation = f.getType().getDeclaredAnnotation(NoParent.class);
        return childNoParentAnnotation != null && childNoParentAnnotation.value();
    }

    private static <T extends WebObject> void initParentWebObjectFields(T webObject, WOProps parentProps) {
        if (WebObject.class.isAssignableFrom(webObject.getClass().getSuperclass())) {
            Class<T> parentClass = (Class<T>) webObject.getClass().getSuperclass();
            while (!parentClass.equals(WebObject.class) && !parentClass.equals(WebComponent.class)) {
                initElements(parentClass, webObject, parentProps);
                parentClass = (Class<T>) parentClass.getSuperclass();
            }
        }
    }

    public static <T extends WebObject> T initWebObject(Class<T> webObjectClass, WebAppInstance<?> appInstance) {
        return reflectionInitWOClass(
                webObjectClass,
                new Class[] {WOProps.class},
                new Object[] {
                        new WOProps(
                                webObjectClass.getDeclaredAnnotation(FindElement.class),
                                webObjectClass,
                                appInstance
                        )
                }
        );
    }

    public static <T extends WebObject> T reflectionInitWOClass(
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
                NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e
        ) {
            throw new FrameworkException("Could not reflectively initialize " + klass + ". Please check that all " +
                    "web components extend WebComponent<T> and have generic <T extends WebObject> type defined.\n" +
                    e.getCause()
            );
        }
    }

    public static <T extends WebObject> T reflectionInitClassWithProps (Class<T> klass, WOProps props) {
        return reflectionInitWOClass(klass, new Class[] {WOProps.class}, new Object[] {props});
    }
}
